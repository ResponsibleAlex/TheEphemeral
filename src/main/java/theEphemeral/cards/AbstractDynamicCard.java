package theEphemeral.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.EphemeralMod;
import theEphemeral.powers.SoothsayerPower;
import theEphemeral.relics.SilkyBandage;

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

        // see if we have any Soothsayer amount left
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(SoothsayerPower.POWER_ID)) {
            return p.getPower(SoothsayerPower.POWER_ID).amount > 0;
        }

        // not the first card, no Soothsayer
        return false;
    }

    protected  boolean triggerFated() {
        if (willTriggerFated()) {
            EphemeralMod.fatedThisTurn++;

            if (AbstractDungeon.player.hasRelic(SilkyBandage.ID)) {
                AbstractDungeon.player.getRelic(SilkyBandage.ID).onTrigger();
            }

            return true;
        }

        return false;
    }
}