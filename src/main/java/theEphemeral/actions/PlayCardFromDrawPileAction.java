package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.cards.AbstractDynamicCard;
import theEphemeral.patches.AbstractCardPatch;

public class PlayCardFromDrawPileAction extends AbstractGameAction {
    private final AbstractCard cardToPlay;
    private final boolean useWaitAction;
    private final float xOffset;

    public PlayCardFromDrawPileAction(AbstractCard cardInDrawPile) {
        this.cardToPlay = cardInDrawPile;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.useWaitAction = true;
        this.xOffset = 0f;
    }
    public PlayCardFromDrawPileAction(AbstractCard cardInDrawPile, boolean useWaitAction, float xOffset) {
        this.cardToPlay = cardInDrawPile;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.useWaitAction = useWaitAction;
        this.xOffset = xOffset;
    }

    @Override
    public void update() {

        if (cardToPlay.type == AbstractCard.CardType.ATTACK
            && AbstractDynamicCard.AreMonstersInvalid()) {

            isDone = true;
            return;
        }

        if (AbstractDungeon.player.drawPile.contains(cardToPlay)) {

            AbstractDungeon.player.drawPile.group.remove(cardToPlay);
            AbstractDungeon.getCurrRoom().souls.remove(cardToPlay);
            AbstractDungeon.player.limbo.group.add(cardToPlay);

            cardToPlay.current_y = -200.0F * Settings.scale;
            cardToPlay.target_x = (float) Settings.WIDTH / 2.0F - (200.0F * Settings.scale) - (xOffset * Settings.scale);
            cardToPlay.target_y = (float) Settings.HEIGHT / 2.0F;
            cardToPlay.targetAngle = 0.0F;
            cardToPlay.lighten(false);
            cardToPlay.drawScale = 0.12F;
            cardToPlay.targetDrawScale = 0.75F;
            cardToPlay.applyPowers();

            AbstractCardPatch.shouldUnlimbo.set(cardToPlay, true);
            this.addToTop(new NewQueueCardAction(cardToPlay, true, false, true));

            if (this.useWaitAction) {
                if (!Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }
        }

        isDone = true;
    }
}
