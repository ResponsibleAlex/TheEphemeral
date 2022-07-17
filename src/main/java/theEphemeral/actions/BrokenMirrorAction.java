package theEphemeral.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BrokenMirrorAction extends AbstractGameAction {
    AbstractPlayer p = AbstractDungeon.player;
    public static final String[] TEXT;
    private AbstractCard dupe;
    private boolean makingChoice = false;

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
            if (p.hand.size() == 0) {
                isDone = true;
                return;
            } else if (p.hand.size() == 1) {
                duplicateAndDisplay(p.hand.getTopCard());

                p.hand.refreshHandLayout();
                tickDuration();
                return;
            } else {
                this.makingChoice = true;
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                this.tickDuration();
                return;
            }
        }

        if (this.makingChoice && !AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            this.makingChoice = false;

            AbstractCard chosen = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);
            duplicateAndDisplay(chosen);
            p.hand.addToTop(chosen);

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

            p.hand.refreshHandLayout();
            this.tickDuration();
            return;
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {

            if (this.dupe != null) {
                this.dupe.shrink();
                AbstractDungeon.player.limbo.removeCard(this.dupe);
                AbstractDungeon.getCurrRoom().souls.onToDeck(this.dupe, false);
            }

            this.isDone = true;
        }
    }

    private void duplicateAndDisplay(AbstractCard c) {

        this.dupe = c.makeStatEquivalentCopy();

        this.dupe.unhover();
        this.dupe.untip();
        this.dupe.stopGlowing();

        this.dupe.current_x = c.current_x;
        this.dupe.current_y = c.current_y;

        AbstractDungeon.player.limbo.addToTop(this.dupe);

        this.dupe.target_x = (float)Settings.WIDTH / 2.0F - Settings.scale * 200.0f;
        this.dupe.target_y = (float)Settings.HEIGHT / 2.0F;
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("DualWieldAction").TEXT;
    }
}
