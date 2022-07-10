package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import theEphemeral.previewWidget.PreviewWidget;

import java.util.List;

public class UpgradeRevealedAction extends AbstractGameAction {

    public UpgradeRevealedAction() {
        actionType = ActionType.CARD_MANIPULATION;
        if (Settings.FAST_MODE) {
            this.startDuration = this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.startDuration = this.duration = Settings.ACTION_DUR_FASTER;
        }
    }
    @Override
    public void update() {
        if (duration == startDuration) {
            List<AbstractCard> cards = PreviewWidget.GetRevealedCards();
            for (AbstractCard c : cards) {
                if (c.canUpgrade()) {
                    c.upgrade();
                    c.applyPowers();
                }
            }
        }
        tickDuration();
    }
}
