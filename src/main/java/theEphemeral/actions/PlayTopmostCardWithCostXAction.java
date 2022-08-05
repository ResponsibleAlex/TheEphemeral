package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlayTopmostCardWithCostXAction extends AbstractGameAction {
    private final AbstractPlayer p = AbstractDungeon.player;
    private final int costToPlay;

    public PlayTopmostCardWithCostXAction(int costOfCardToPlay) {
        this.costToPlay = costOfCardToPlay;
        this.actionType = ActionType.SPECIAL;
    }


    @Override
    public void update() {
        AbstractCard cardToPlay = null;
        for (int i = p.drawPile.group.size() - 1; i >= 0; i--) {
            if (p.drawPile.group.get(i).costForTurn == costToPlay) {
                cardToPlay = p.drawPile.group.get(i);
                break;
            }
        }

        if (cardToPlay != null) {
            this.addToTop(new PlayCardFromDrawPileAction(cardToPlay));
        }

        this.isDone = true;
    }
}
