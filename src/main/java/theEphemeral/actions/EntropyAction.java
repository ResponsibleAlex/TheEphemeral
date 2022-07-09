package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.cards.AbstractEntropyCard;

public class EntropyAction extends AbstractGameAction {
    private final int increment;
    private final AbstractPlayer player;

    public EntropyAction(int increment) {
        actionType = ActionType.CARD_MANIPULATION;
        player = AbstractDungeon.player;

        this.increment = increment;
    }

    @Override
    public void update() {
        player.limbo.group.forEach(x -> {
            if (x instanceof AbstractEntropyCard) {
                ((AbstractEntropyCard) x).increaseEntropy(increment);
                x.applyPowers();
            }
        });

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
