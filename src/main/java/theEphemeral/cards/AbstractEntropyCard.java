package theEphemeral.cards;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.actions.EntropyAction;
import theEphemeral.powers.ChaosRiftPower;

public abstract class AbstractEntropyCard extends AbstractDynamicCard {

    public AbstractEntropyCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }

    protected void entropyAction() {
        int increment = 1;

        if (AbstractDungeon.player.hasPower(ChaosRiftPower.POWER_ID)) {
            increment += AbstractDungeon.player.getPower(ChaosRiftPower.POWER_ID).amount;
        }

        // increase this card's values
        increaseEntropy(increment);
        // increase all other entropy cards' values
        addToBot(new EntropyAction(increment));
    }

    public void increaseEntropy(int increment) {
        if (baseDamage > 0) {
            baseDamage += increment;
        }
        if (baseBlock > 0) {
            baseBlock += increment;
        }
    }
}
