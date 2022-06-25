package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.previewWidget.PreviewWidget;

public class UpgradeRevealedAction extends AbstractGameAction {
    private final AbstractPlayer player;

    public UpgradeRevealedAction() {
        actionType = ActionType.CARD_MANIPULATION;
        player = AbstractDungeon.player;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
    }
    @Override
    public void update() {
        if (duration == startDuration) {
            int revealedIndex = PreviewWidget.revealed - 1;
            for (AbstractCard c : player.drawPile.group.subList(0, revealedIndex)) {
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
