package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
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

        float xOffset = 0f;
        for (AbstractCard c : candidates.group) {
            addToBot(new PlayCardFromDrawPileAction(c, false, xOffset));
            xOffset += 40f;
        }

        if (!Settings.FAST_MODE) {
            this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        } else {
            this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }

        isDone = true;
    }
}
