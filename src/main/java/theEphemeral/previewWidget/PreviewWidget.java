package theEphemeral.previewWidget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.relics.FrozenEye;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import theEphemeral.EphemeralMod;
import theEphemeral.actions.LinenVeilAction;
import theEphemeral.cards.AbstractVanishingCard;
import theEphemeral.powers.DarkContractPower;
import theEphemeral.relics.LinenVeil;
import theEphemeral.variables.TopCardMode;
import theEphemeral.vfx.FeatherEffect;

import java.util.ArrayList;
import java.util.List;

import static theEphemeral.EphemeralMod.makeEffectPath;

public class PreviewWidget {
    public static final int MAX_AUGURY = 7;
    public static final int AUGURY_MINUS_PER_TURN = 1;
    private static final float PREVIEW_CARD_SCALE = 0.5f;
    private static final float HOVER_CARD_SCALE = 1.0f;
    private static final float HOVER_CARD_SCALE_START = 0.95f;
    private static final float HEADER_WIDTH = 150.0f;
    private static final float HALF_HEADER_WIDTH = HEADER_WIDTH / 2.0f;
    private static final float HEADER_HEIGHT = 50.0f;
    private static final Texture HEADER_IMG = ImageMaster.loadImage(makeEffectPath("header.png"));
    private static final float WIDGET_X = 150.0f;
    private static final float AMOUNT_TEXT_X = WIDGET_X + HALF_HEADER_WIDTH - 20.0f;
    private static final float WIDGET_Y = 780.0f;
    private static final float CARD_Y = WIDGET_Y - 150.0f;
    private static final float X_PADDING = 25.0f;
    private static final float TOOLTIP_X = WIDGET_X + HALF_HEADER_WIDTH + X_PADDING;
    private static final float HOVER_X = WIDGET_X + HALF_HEADER_WIDTH + X_PADDING + 155.0f;

    private static final String POWER_ID = EphemeralMod.makeID(PreviewWidget.class.getSimpleName());
    private static final PowerStrings tipStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String TIP_NAME = tipStrings.DESCRIPTIONS[0];
    private static final String TIP_DESC_1 = tipStrings.DESCRIPTIONS[1];
    private static final String TIP_DESC_2 = tipStrings.DESCRIPTIONS[2];

    // CardGroup that is displayed
    private final CardGroup previews = new CardGroup(CardGroupType.UNSPECIFIED);
    private int drawPileCount = 0;
    private AbstractCard hoveredCard;

    private int augury = 0;
    private int startOfTurnMod = 0;
    private boolean firstTurn = true;

    private final Hitbox hb;
    private final AbstractPlayer p;
    protected float fontScale;
    private float timer = 0.0F;

    // instance of widget created at start of combat and destroyed at end of combat
    private static PreviewWidget widget;
    public static void StartOfCombat() {
        widget = new PreviewWidget();
        LinenVeil.Mode = TopCardMode.None;
    }
    public static void EndOfCombat() {
        Clear();
        widget = null;
    }

    public PreviewWidget() {
        //Settings.isDebug = true;
        hb = new Hitbox(HEADER_WIDTH * Settings.scale, HEADER_HEIGHT * Settings.scale);
        p = AbstractDungeon.player;
        fontScale = 0.7F;
    }

    public static void Clear() {
        if (widget != null && widget.isActive())
            widget.clear();
    }
    public void clear() {
        augury = 0;
        previews.clear();
    }

    public static void Update() {
        if (widget != null && widget.isActive())
            widget.update();
    }
    public void update() {
        // check to see if the draw pile has changed since last update
        if (needUpdate()) {
            // clear old previews
            previews.clear();

            // add copies to the CardGroup and initialize visuals
            if (getRevealed() > 0) {
                if (EphemeralMod.reversePreviews)
                    fillPreviewsReversed();
                else
                    fillPreviews();
            }

            for (AbstractCard c : p.hand.group) {
                c.applyPowers();
            }

            // previews are updated, trigger Linen Veil if we have it
            if (p.hasRelic(LinenVeil.ID)) {
                AbstractDungeon.actionManager.addToBottom(new LinenVeilAction());
            }
        }

        previews.update();

        boolean hovering = false;
        if (previews.size() > 0) {
            for (int i = previews.group.size() - 1; i >= 0; i--) {
                AbstractCard c = previews.group.get(i);
                if (c.isHoveredInHand(PREVIEW_CARD_SCALE)) {
                    hovering = true;
                    if (hoveredCard == null || notEqual(hoveredCard, c)) {
                        hoveredCard = c.makeStatEquivalentCopy();
                        hoveredCard.applyPowers();

                        hoveredCard.setAngle(0.0F, true);
                        hoveredCard.current_x = hoveredCard.target_x = Settings.scale * HOVER_X;
                        hoveredCard.current_y = hoveredCard.target_y = Settings.scale * CARD_Y;
                        hoveredCard.drawScale = HOVER_CARD_SCALE_START;
                        hoveredCard.targetDrawScale = HOVER_CARD_SCALE;
                        hoveredCard.lighten(true);
                    }
                    break;
                }
            }
        }
        if (hovering)
            hoveredCard.update();
        else
            hoveredCard = null;

        hb.resize(HEADER_WIDTH * Settings.scale, HEADER_HEIGHT * Settings.scale);
        hb.move(Settings.scale * WIDGET_X, Settings.scale * WIDGET_Y);
        hb.update();

        if (augury > 0 && hb.hovered) {
            TipHelper.renderGenericTip(
                    Settings.scale * TOOLTIP_X,
                    Settings.scale * WIDGET_Y,
                    TIP_NAME, TIP_DESC_1 + augury + TIP_DESC_2);
        }
        fontScale = MathHelper.scaleLerpSnap(fontScale, 0.7F);

        if (augury > 0) {
            timer -= Gdx.graphics.getDeltaTime();
            if (0.0F > timer) {
                timer += 0.3F;
                AbstractDungeon.effectsQueue.add(new FeatherEffect(WIDGET_X, WIDGET_Y));
            }
        }
    }

    private boolean needUpdate() {
        if (p.drawPile.size() != drawPileCount) {
            drawPileCount = p.drawPile.size();
            return true;
        }

        ArrayList<AbstractCard> drawGroup = p.drawPile.group;
        ArrayList<AbstractCard> preGroup = previews.group;

        if (previews.size() != getRevealed())
            return true;

        int revealedIndex = getRevealed() - 1;
        int drawPileIndexOffset = p.drawPile.size() - 1;

        if (EphemeralMod.reversePreviews) {
            for (int i = revealedIndex; i >= 0; i--) {
                if (notEqual(drawGroup.get(drawPileIndexOffset - i), preGroup.get(revealedIndex - i)))
                    return true;
            }
        } else {
            for (int i = revealedIndex; i >= 0; i--) {
                if (notEqual(drawGroup.get(drawPileIndexOffset - i), preGroup.get(i)))
                    return true;
            }
        }

        return false;
    }
    private boolean notEqual(AbstractCard a, AbstractCard b) {
        return a.uuid != b.uuid
                || a.upgraded != b.upgraded;
    }
    private void fillPreviews() {
        CardGroup drawPile = p.drawPile;

        int revealedIndex = getRevealed() - 1;
        int drawPileIndexOffset = drawPile.size() - 1;

        for (int i = revealedIndex; i >= 0; i--) {
            AbstractCard c = drawPile.group.get(drawPileIndexOffset - i);

            AbstractCard cpy = c.makeSameInstanceOf();
            if (cpy instanceof AbstractVanishingCard)
                cpy.uuid = c.uuid;

            cpy.applyPowers();
            cpy.setAngle(0.0F, true);
            cpy.current_x = cpy.target_x = Settings.scale * WIDGET_X;
            cpy.current_y = cpy.target_y = Settings.scale * (CARD_Y - (i * 40));
            cpy.targetDrawScale = cpy.drawScale = PREVIEW_CARD_SCALE;
            cpy.lighten(true);

            previews.addToBottom(cpy);
        }
    }
    private void fillPreviewsReversed() {
        CardGroup drawPile = p.drawPile;

        int revealedIndex = getRevealed() - 1;
        int drawPileIndexOffset = drawPile.size() - 1;

        for (int i = revealedIndex; i >= 0; i--) {
            AbstractCard c = drawPile.group.get(drawPileIndexOffset - i);

            AbstractCard cpy = c.makeSameInstanceOf();
            if (cpy instanceof AbstractVanishingCard)
                cpy.uuid = c.uuid;

            cpy.applyPowers();
            cpy.setAngle(0.0F, true);
            cpy.current_x = cpy.target_x = Settings.scale * WIDGET_X;
            cpy.current_y = cpy.target_y = Settings.scale * (CARD_Y - ((revealedIndex - i) * 40));
            cpy.targetDrawScale = cpy.drawScale = PREVIEW_CARD_SCALE;
            cpy.lighten(true);

            previews.addToTop(cpy);
        }


    }

    public static int GetAugury() {
        if (widget != null && widget.isActive())
            return widget.getAugury();
        else
            return 0;
    }
    private int getAugury() {
        return augury;
    }

    public static void SetAugury(int newValue) {
        if (widget != null && widget.isActive())
            widget.setAugury(newValue);
    }
    private void setAugury(int newValue) {
        augury = newValue;

        if (augury < 0)
            augury = 0;
        if (augury > MAX_AUGURY)
            augury = MAX_AUGURY;
    }

    public static void AddAugury(int amount) {
        if (widget != null && widget.isActive())
            widget.addAugury(amount);
    }
    private void addAugury(int amount) {
        if (amount > 0 && AbstractDungeon.player.hasPower(DarkContractPower.POWER_ID)) {
            AbstractDungeon.player.getPower(DarkContractPower.POWER_ID).onSpecificTrigger();
        }

        SetAugury(amount + augury);
    }

    public static void StartOfTurn() {
        if (widget != null && widget.isActive())
            widget.startOfTurn();
    }
    private void startOfTurn() {
        startOfTurnMod = 0;
    }
    public static void StartOfTurnIncrease(int amount) {
        if (widget != null && widget.isActive())
            widget.startOfTurnIncrease(amount);
    }
    private void startOfTurnIncrease(int amount) {
        startOfTurnMod += amount;
    }

    public static void StartOfTurnAccounting() {
        if (widget != null && widget.isActive())
            widget.startOfTurnAccounting();
    }
    private void startOfTurnAccounting() {
        if (firstTurn) {
            firstTurn = false;
        } else {
            if (!AbstractDungeon.player.hasRelic(FrozenEye.ID)) {
                startOfTurnMod -= AUGURY_MINUS_PER_TURN;
            }

            AddAugury(startOfTurnMod);
        }
    }

    private int getRevealed() {
        return Math.min(augury, p.drawPile.size());
    }

    public static List<AbstractCard> GetRevealedCards() {
        if (AbstractDungeon.player != null
           && AbstractDungeon.player.drawPile.size() == 0)
            return new ArrayList<>();

        if (widget != null && widget.isActive())
            return widget.getRevealedCards();
        else
            return new ArrayList<>();
    }
    private List<AbstractCard> getRevealedCards() {
        CardGroup g = new CardGroup(CardGroupType.UNSPECIFIED);

        if (getRevealed() > 0) {
            int revealedIndex = getRevealed() - 1;
            int drawPileIndexOffset = p.drawPile.size() - 1;

            for (int i = revealedIndex; i >= 0; i--) {
                g.addToBottom(p.drawPile.group.get(drawPileIndexOffset - i));
            }
        }

        return g.group;
    }

    public static int GetRevealedAttacksCount() {
        if (widget != null && widget.isActive())
            return widget.getRevealedAttacksCount();
        else
            return 0;
    }
    private int getRevealedAttacksCount() {
        return (int) previews.group.stream().filter(x -> x.type == AbstractCard.CardType.ATTACK).count();
    }

    public static void Render(SpriteBatch sb) {
        if (widget != null && widget.isActive())
            widget.render(sb);
    }
    private void render(SpriteBatch sb) {
        if (augury > 0) {
            sb.setColor(1.0f, 1.0f, 1.0f, 1.0f);

            for (AbstractCard c : previews.group) {
                c.render(sb);
            }

            if (hoveredCard != null)
                hoveredCard.render(sb);

            sb.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            drawHeader(sb);
            renderText(sb);
            hb.render(sb);
        }
    }
    private void drawHeader(SpriteBatch sb) {
        sb.draw(HEADER_IMG,
                hb.x,
                hb.y,
                0, 0,
                HEADER_WIDTH, HEADER_HEIGHT,
                Settings.scale,
                Settings.scale,
                0, 0, 0,
                (int)HEADER_WIDTH, (int)HEADER_HEIGHT,
                false, false);
    }
    private void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                Integer.toString(augury),
                Settings.scale * (AMOUNT_TEXT_X),
                Settings.scale * (WIDGET_Y),
                new Color(1.0f, 1.0f, 1.0f, 1.0f), fontScale * Settings.scale);
    }

    private boolean isActive() {
        return (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getCurrRoom() instanceof MonsterRoom) && !p.isDead;
    }
}
