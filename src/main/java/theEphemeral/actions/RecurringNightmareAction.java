package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RecurringNightmareAction extends AbstractGameAction {
    public static final String TEXT;
    private final AbstractPlayer player;

    public RecurringNightmareAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.player.discardPile.size() == 1) {
                // only 1 card in discard, queue it and done
                addToBot(new NewQueueCardAction(this.player.discardPile.getTopCard(), true, false, true));
                this.isDone = true;
            } else if (this.player.discardPile.size() > 1) {
                // more than 1 card in discard, open select screen and tick
                AbstractDungeon.gridSelectScreen.open(this.player.discardPile, 1, TEXT, false);
                this.tickDuration();
            } else {
                // no cards in discard, just done
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                addToBot(new NewQueueCardAction(AbstractDungeon.gridSelectScreen.selectedCards.get(0), true, false, true));

                for (AbstractCard c : this.player.discardPile.group) {
                    c.unhover();
                    c.target_x = (float) CardGroup.DISCARD_PILE_X;
                    c.target_y = 0.0F;
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }

            this.tickDuration();
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("WishAction").TEXT[0];
    }
}
