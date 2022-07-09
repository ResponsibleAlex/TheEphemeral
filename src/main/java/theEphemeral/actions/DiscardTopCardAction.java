package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardTopCardAction extends AbstractGameAction {
    private final AbstractPlayer player;

    public DiscardTopCardAction() {
        actionType = ActionType.CARD_MANIPULATION;
        player = AbstractDungeon.player;
        if (Settings.FAST_MODE) {
            this.startDuration = this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.startDuration = this.duration = Settings.ACTION_DUR_FASTER;
        }
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            if (player.drawPile.size() > 0) {
                AbstractCard c = player.drawPile.getTopCard();
                player.drawPile.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
            }
        }
        tickDuration();
    }
}
