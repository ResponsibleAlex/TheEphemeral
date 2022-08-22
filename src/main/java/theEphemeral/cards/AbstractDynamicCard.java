package theEphemeral.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.EphemeralMod;
import theEphemeral.powers.KismetPower;
import theEphemeral.relics.HookAndYarn;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractDynamicCard extends AbstractDefaultCard {

    protected boolean fated = false;

    public AbstractDynamicCard(final String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
        if (type == CardType.ATTACK && target == CardTarget.ALL_ENEMY) {
            isMultiDamage = true;
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (willTriggerFated()) {
            glowColor = Color.FIREBRICK.cpy();
        } else {
            glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    protected boolean willTriggerFated() {
        // check if this card is set to "fated"
        if (!fated)
            return false;

        // if it's the first Fated card, always true
        if (EphemeralMod.fatedThisTurn == 0) {
            return true;
        }

        // see if we have any Kismet amount left
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(KismetPower.POWER_ID)) {
            return p.getPower(KismetPower.POWER_ID).amount > 0;
        }

        // not the first card, no Kismet
        return false;
    }

    protected boolean triggerFated() {
        if (willTriggerFated()) {
            EphemeralMod.fatedThisTurn++;

            if (AbstractDungeon.player.hasRelic(HookAndYarn.ID)) {
                AbstractDungeon.player.getRelic(HookAndYarn.ID).onTrigger();
            }

            return true;
        }

        return false;
    }

    protected int kismet() {
        if (AbstractDungeon.player.hasPower(KismetPower.POWER_ID))
            return ((KismetPower)AbstractDungeon.player.getPower(KismetPower.POWER_ID)).fullAmount;

        return 0;
    }

    protected void megaShuffle() {
        if (AbstractDungeon.player.discardPile.size() > 0) {
            addToBot(new EmptyDeckShuffleAction());
            addToBot(new ShuffleAction(AbstractDungeon.player.drawPile, false));
        } else if (AbstractDungeon.player.drawPile.size() > 0) {
            addToBot(new ShuffleAction(AbstractDungeon.player.drawPile, true));
        }
    }

    protected void megaShuffleTop() {
        if (AbstractDungeon.player.discardPile.size() > 0) {
            addToTop(new ShuffleAction(AbstractDungeon.player.drawPile, false));
            addToTop(new EmptyDeckShuffleAction());
        } else if (AbstractDungeon.player.drawPile.size() > 0) {
            addToTop(new ShuffleAction(AbstractDungeon.player.drawPile, true));
        }
    }
}