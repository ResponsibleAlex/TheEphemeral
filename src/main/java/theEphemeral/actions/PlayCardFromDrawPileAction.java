package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlayCardFromDrawPileAction extends AbstractGameAction {
    private final AbstractCard cardToPlay;

    public PlayCardFromDrawPileAction(AbstractCard cardInDrawPile) {
        this.cardToPlay = cardInDrawPile;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {

        if (AbstractDungeon.player.drawPile.contains(cardToPlay)) {

            AbstractDungeon.player.drawPile.group.remove(cardToPlay);
            AbstractDungeon.getCurrRoom().souls.remove(cardToPlay);

            cardToPlay.current_y = -200.0F * Settings.scale;
            cardToPlay.target_x = (float) Settings.WIDTH / 2.0F - (200.0F * Settings.scale);
            cardToPlay.target_y = (float) Settings.HEIGHT / 2.0F;
            cardToPlay.targetAngle = 0.0F;
            cardToPlay.lighten(false);
            cardToPlay.drawScale = 0.12F;
            cardToPlay.targetDrawScale = 0.75F;
            cardToPlay.applyPowers();

            this.addToTop(new NewQueueCardAction(cardToPlay, true, false, true));
        }

        isDone = true;
    }
}
