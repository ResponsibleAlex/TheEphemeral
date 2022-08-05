package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.cards.BlindFury;

public class BlindFuryAction extends AbstractGameAction {
    public BlindFuryAction() {
        actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        CardGroup candidates = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.type == AbstractCard.CardType.ATTACK
                    && !(c instanceof BlindFury)) {
                candidates.addToRandomSpot(c);
            }
        }

        if (candidates.size() > 0) {
            this.addToTop(new PlayCardFromDrawPileAction(candidates.getTopCard()));
        }

        isDone = true;
    }
}
