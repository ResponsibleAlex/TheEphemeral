package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.patches.AbstractCardPatch;

public class PlayCardFromDiscardPileAction extends AbstractGameAction {

    private final AbstractCard card;
    private final AbstractPlayer player;

    public PlayCardFromDiscardPileAction(AbstractCard cardToPlay) {
        this.card = cardToPlay;
        this.player = AbstractDungeon.player;
    }

    @Override
    public void update() {
        AbstractDungeon.getCurrRoom().souls.remove(card);
        this.player.discardPile.removeCard(card);
        this.player.limbo.group.add(card);

        card.current_y = -200.0F * Settings.scale;
        card.target_x = (float)Settings.WIDTH / 2.0F;
        card.target_y = (float)Settings.HEIGHT / 2.0F;
        card.angle = 270.0F;
        card.targetAngle = 0.0F;
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.applyPowers();

        AbstractCardPatch.shouldUnlimbo.set(card, true);

        this.addToTop(new NewQueueCardAction(card, true, false, true));
        if (!Settings.FAST_MODE) {
            this.addToTop(new WaitAction(Settings.ACTION_DUR_LONG));
        } else {
            this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        }

        this.isDone = true;
    }
}
