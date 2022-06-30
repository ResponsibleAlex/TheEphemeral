package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import theEphemeral.previewWidget.PreviewWidget;

public class UpgradeRevealedAction extends AbstractGameAction {

    public UpgradeRevealedAction() {
        actionType = ActionType.CARD_MANIPULATION;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
    }
    @Override
    public void update() {
        if (duration == startDuration) {
            for (AbstractCard c : PreviewWidget.GetRevealedCards()) {
                if (c.canUpgrade()) {
                    c.upgrade();
                    c.superFlash();
                    c.applyPowers();
                }
            }
        }
        tickDuration();
    }
}
