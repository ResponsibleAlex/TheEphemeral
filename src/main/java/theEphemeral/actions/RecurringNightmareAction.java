package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.cards.RecurringNightmare;

import java.util.ArrayList;

public class RecurringNightmareAction extends AbstractGameAction {
    public static final String TEXT;
    private final AbstractPlayer player;
    private final ArrayList<AbstractCard> nightmares;

    public RecurringNightmareAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;

        nightmares = new ArrayList<>();
    }

    public void update() {
        if (this.duration == this.startDuration) {

            //
            // temporarily remove other copies of Recurring Nightmare
            stashNightmares();

            //
            // with Nightmares removed, act based on size of discard pile
            if (this.player.discardPile.size() == 1) {
                // only 1 card in discard, queue it and done
                this.addToTop(new PlayCardFromDiscardPileAction(this.player.discardPile.getTopCard()));

                returnNightmaresAndDone();
            } else if (this.player.discardPile.size() > 1) {
                // more than 1 card in discard, open select screen and tick

                AbstractDungeon.gridSelectScreen.open(this.player.discardPile, 1, TEXT, false);
                this.tickDuration();
            } else {
                // no cards in discard, just done
                returnNightmaresAndDone();
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

                this.addToTop(new PlayCardFromDiscardPileAction(AbstractDungeon.gridSelectScreen.selectedCards.get(0)));

                for (AbstractCard c : this.player.discardPile.group) {
                    c.unhover();
                    c.target_x = (float) CardGroup.DISCARD_PILE_X;
                    c.target_y = 0.0F;
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();

                returnNightmaresAndDone();
            }

            this.tickDuration();
        }
    }

    private void stashNightmares() {
        // get any copies of Recurring Nightmare to prevent easy infinites
        for (AbstractCard c : this.player.discardPile.group) {
            if (c instanceof RecurringNightmare) {
                nightmares.add(c);
            }
        }
        // remove them from the discard pile temporarily
        for (AbstractCard c : nightmares) {
            this.player.discardPile.removeCard(c);
        }
    }

    private void returnNightmaresAndDone() {
        if (nightmares.size() > 0) {
            this.player.discardPile.group.addAll(nightmares);
        }
        this.isDone = true;
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("WishAction").TEXT[0];
    }
}
