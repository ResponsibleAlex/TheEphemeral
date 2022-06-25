package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.cards.AbstractEntropyCard;

public class EntropyAction extends AbstractGameAction {
    private final AbstractPlayer player;

    public EntropyAction() {
        actionType = ActionType.CARD_MANIPULATION;
        player = AbstractDungeon.player;
    }

    @Override
    public void update() {
        player.drawPile.group.forEach(x -> {
            if (x instanceof AbstractEntropyCard) {
                ((AbstractEntropyCard) x).increaseEntropy();
                x.applyPowers();
            }
        });

        player.discardPile.group.forEach(x -> {
            if (x instanceof AbstractEntropyCard) {
                ((AbstractEntropyCard) x).increaseEntropy();
                x.applyPowers();
            }
        });

        player.hand.group.forEach(x -> {
            if (x instanceof AbstractEntropyCard) {
                ((AbstractEntropyCard) x).increaseEntropy();
                x.applyPowers();
            }
        });

        isDone = true;
    }
}
