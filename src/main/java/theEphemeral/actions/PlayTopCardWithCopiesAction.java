package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.patches.AbstractCardPatch;

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
            for (int i = 0; i < copies; i++) {
                this.playCopy(c, i);
            }
            this.playCard(c);

            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            }

            this.isDone = true;
        }
    }

    private void playCopy(AbstractCard card, int offset) {
        float offsetX = -(offset + 1) * 50 * Settings.scale;

        AbstractCard copy = card.makeSameInstanceOf();

        copy.current_x = card.current_x;
        copy.current_y = card.current_y;

        this.queueCard(copy, offsetX, true);
    }

    private void playCard(AbstractCard card) {

        AbstractDungeon.player.drawPile.group.remove(card);
        AbstractDungeon.getCurrRoom().souls.remove(card);

        this.queueCard(card, 200 * Settings.scale, false);
    }

    private void queueCard(AbstractCard card, float offsetX, boolean purgeOnUse) {

        AbstractDungeon.player.limbo.addToBottom(card);

        card.target_x = (float) Settings.WIDTH / 2.0F - (300.0F * Settings.scale) + offsetX;
        card.target_y = (float)Settings.HEIGHT / 2.0F;

        card.targetAngle = 0.0F;
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.applyPowers();

        card.purgeOnUse = purgeOnUse;

        if (!purgeOnUse)
            AbstractCardPatch.shouldUnlimbo.set(card, true);

        this.addToTop(new NewQueueCardAction(card, true, false, true));

    }
}
