package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.cards.BrokenMirror;

import java.util.List;

public class BrokenMirrorAction extends AbstractGameAction {
    public static final String[] TEXT;

    public BrokenMirrorAction() {
        actionType = ActionType.CARD_MANIPULATION;
        if (Settings.FAST_MODE)
            duration = startDuration = Settings.ACTION_DUR_FASTER;
        else
            duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (duration == startDuration) {

            // get all the Revealed cards except other BrokenMirrors
            List<AbstractCard> revealed = BrokenMirror.GetNonBrokenRevealed();
            int revealedCount = revealed.size();

            if (revealedCount == 0) {
                isDone = true;
                return;

            } else if (revealedCount == 1) {
                addToTop(new MakeTempCardInHandAction(revealed.get(0).makeStatEquivalentCopy()));
                tickDuration();
                return;

            } else {
                CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard c : revealed) {
                    tmpGroup.addToTop(c);
                }
                AbstractDungeon.gridSelectScreen.open(tmpGroup, 1, false, TEXT[0]);

                this.tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard chosen = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            addToTop(new MakeTempCardInHandAction(chosen.makeStatEquivalentCopy()));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

            this.tickDuration();
            return;
        }

        this.tickDuration();
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("DualWieldAction").TEXT;
    }
}
