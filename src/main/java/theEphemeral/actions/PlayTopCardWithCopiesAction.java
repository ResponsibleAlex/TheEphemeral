package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlayTopCardWithCopiesAction extends AbstractGameAction {
    private final AbstractPlayer p = AbstractDungeon.player;
    private final int copies;

    public PlayTopCardWithCopiesAction(int numberOfCopies) {
        copies = numberOfCopies;
    }

    @Override
    public void update() {
        if (p.drawPile.isEmpty() && p.discardPile.isEmpty()) {
            this.isDone = true;
        } else if (p.drawPile.isEmpty()) {
            this.addToTop(new PlayTopCardWithCopiesAction(copies));
            this.addToTop(new EmptyDeckShuffleAction());
            this.isDone = true;
        } else {
            AbstractCard c = p.drawPile.getTopCard();
            this.addToTop(new PlayCardFromDrawPileAction(c));
            for (int i = 0; i < copies; i++) {
                playCopy(c);
            }
            this.isDone = true;
        }
    }

    private void playCopy(AbstractCard card) {
        AbstractCard tmp = card.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tmp);

        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        tmp.target_y = (float)Settings.HEIGHT / 2.0F;

        tmp.targetAngle = 0.0F;
        tmp.lighten(false);
        tmp.drawScale = 0.12F;
        tmp.targetDrawScale = 0.75F;
        tmp.applyPowers();

        tmp.purgeOnUse = true;
        AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(tmp, true, false, true));
        AbstractDungeon.actionManager.addToTop(new UnlimboAction(tmp));
        if (!Settings.FAST_MODE) {
            AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        } else {
            AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }
    }
}
