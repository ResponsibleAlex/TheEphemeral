package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TemporalTonicAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final int amount;

    public TemporalTonicAction(int potency) {
        p = AbstractDungeon.player;
        amount = potency;
    }

    @Override
    public void update() {
        if (p.drawPile.size() == 0 && p.discardPile.size() == 0) {
            isDone = true;
        } else if (p.drawPile.size() == 0) {
            addToTop(new TemporalTonicAction(amount));
            addToTop(new EmptyDeckShuffleAction());
            isDone = true;
        } else {
            int dupes = amount - 1;
            AbstractCard c = p.drawPile.getTopCard();
            for (int i = 0; i < dupes; i++) {
                playCopy(c);
            }

            AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            addToTop(new PlayTopCardAction(m, false));
        }
    }

    private void playCopy(AbstractCard card) {
        AbstractCard tmp = card.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        tmp.target_y = (float)Settings.HEIGHT / 2.0F;

        tmp.purgeOnUse = true;
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, true, card.energyOnUse, true, true), true);
    }
}
