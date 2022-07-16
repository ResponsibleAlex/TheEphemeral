package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class BrokenMirrorAction extends AbstractGameAction {
    AbstractPlayer p = AbstractDungeon.player;
    public static final String[] TEXT;

    public BrokenMirrorAction() {
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            if (p.hand.size() == 0) {
                isDone = true;
                return;
            } else if (p.hand.size() == 1) {
                duplicateToTop(p.hand.getTopCard());

                p.hand.refreshHandLayout();
                isDone = true;
                return;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                this.tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard chosen = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);
            duplicateToTop(chosen);
            p.hand.addToTop(chosen);

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

            p.hand.refreshHandLayout();
            this.isDone = true;
        }

        this.tickDuration();
    }

    private void duplicateToTop(AbstractCard c) {
        AbstractCard cardToAdd = c.makeStatEquivalentCopy();
        cardToAdd.current_x = -1000.0F * Settings.xScale;
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(cardToAdd,
                (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F,
                false, false, false));
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("DualWieldAction").TEXT;
    }
}
