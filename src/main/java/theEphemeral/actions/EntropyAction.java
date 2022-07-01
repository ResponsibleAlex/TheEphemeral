package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.cards.AbstractEntropyCard;
import theEphemeral.powers.ChaosRiftPower;

public class EntropyAction extends AbstractGameAction {
    private final int increment;
    private final AbstractPlayer player;

    public EntropyAction() {
        actionType = ActionType.CARD_MANIPULATION;
        player = AbstractDungeon.player;

        int incrementVal = 1;

        if (player.hasPower(ChaosRiftPower.POWER_ID)) {
            incrementVal += player.getPower(ChaosRiftPower.POWER_ID).amount;
        }

        increment = incrementVal;
    }

    @Override
    public void update() {
        player.drawPile.group.forEach(x -> {
            if (x instanceof AbstractEntropyCard) {
                ((AbstractEntropyCard) x).increaseEntropy(increment);
                x.applyPowers();
            }
        });

        player.discardPile.group.forEach(x -> {
            if (x instanceof AbstractEntropyCard) {
                ((AbstractEntropyCard) x).increaseEntropy(increment);
                x.applyPowers();
            }
        });

        player.hand.group.forEach(x -> {
            if (x instanceof AbstractEntropyCard) {
                ((AbstractEntropyCard) x).increaseEntropy(increment);
                x.applyPowers();
            }
        });

        isDone = true;
    }
}
