package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import theEphemeral.previewWidget.PreviewWidget;

public class TemporalSurgeAction extends AbstractGameAction {

    public TemporalSurgeAction() {
        actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        CardGroup candidates = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : PreviewWidget.GetRevealedCards()) {
            if (c.type == AbstractCard.CardType.ATTACK || c.type == AbstractCard.CardType.SKILL) {
                candidates.addToTop(c);
            }
        }

        for (AbstractCard c : candidates.group) {
            addToBot(new PlayCardFromDrawPileAction(c));
        }

        isDone = true;
    }
}
