package theEphemeral.previewWidget;

import com.megacrit.cardcrawl.relics.FrozenEye;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class PreviewWidget {
    public static final int MAX_AUGURY = 7;

    // CardGroup that is actually getting displayed
    private static final CardGroup previews = new CardGroup(CardGroupType.UNSPECIFIED);

    // clone of DrawPile to compare for updates
    private static final CardGroup drawPileCopy = new CardGroup(CardGroupType.UNSPECIFIED);

    private static final float drawScale = 0.5f;

    private static int augury = 0;
    private static int startOfTurnMod = 0;

    public static void Clear() {
        augury = 0;
        SetCards();
    }

    public static void SetCards() {
        CardGroup drawPile = AbstractDungeon.player.drawPile;

        // clear old previews
        previews.clear();

        // add copies to the CardGroup and initialize visuals
        if (GetRevealed() > 0) {
            for (int i = GetRevealedIndex(); i >= 0; i--) {
                AbstractCard c = drawPile.group.get(i);
                AbstractCard cpy = c.makeStatEquivalentCopy();

                cpy.setAngle(0.0F, true);
                cpy.current_x = cpy.target_x = Settings.scale * 20;//AbstractCard.IMG_WIDTH;
                cpy.current_y = cpy.target_y = Settings.scale * (Settings.HEIGHT * 0.75f + (i * 40)) ;
                cpy.targetDrawScale = drawScale;
                cpy.lighten(true);

                previews.addToTop(cpy);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void Update() {
        // check to see if the draw pile has changed since last update
        CardGroup drawPile = AbstractDungeon.player.drawPile;
        if (!drawPile.group.equals(drawPileCopy.group)) {
            // draw pile has changed, refresh our copy
            drawPileCopy.group = (ArrayList<AbstractCard>) drawPile.group.clone();

            // reset the previews based on the new draw pile
            SetCards();
        }

        previews.update();
    }

    public static int GetAugury() {
        return augury;
    }

    public static void SetAugury(int newValue) {
        augury = newValue;

        if (augury < 0)
            augury = 0;
        if (augury > MAX_AUGURY)
            augury = MAX_AUGURY;
    }

    public static void AddAugury(int amount) {
        SetAugury(amount + augury);
    }

    public static void StartOfTurn() {
        startOfTurnMod = 0;
    }
    public static void StartOfTurnIncrease(int amount) {
        startOfTurnMod += amount;
    }

    public static void StartOfTurnAccounting() {
        if (!AbstractDungeon.player.hasRelic(FrozenEye.ID)) {
            startOfTurnMod -= 2;
        }

        AddAugury(startOfTurnMod);
    }

    public static int GetRevealed() {
        return Math.min(augury, AbstractDungeon.player.drawPile.size());
    }

    private static int GetRevealedIndex() {
        return GetRevealed() - 1;
    }

    public static List<AbstractCard> GetRevealedCards() {
        return AbstractDungeon.player.drawPile.group.subList(0, PreviewWidget.GetRevealedIndex());
    }

    public static int GetRevealedAttacksCount() {
        return (int) previews.group.stream().filter(x -> x.type == AbstractCard.CardType.ATTACK).count();
    }

    public static void Render(SpriteBatch sb) {
        previews.render(sb);
    }

}
