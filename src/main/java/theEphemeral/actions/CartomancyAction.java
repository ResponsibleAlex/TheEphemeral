package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

import java.util.ArrayList;

public class CartomancyAction extends AbstractGameAction {

    private boolean retrieveCard = false;
    private final boolean setCostToZero;

    public CartomancyAction(boolean upgraded) {
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        setCostToZero = upgraded;
    }
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(), CardRewardScreen.TEXT[1], true);
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();

                    if (AbstractDungeon.player.hasPower("MasterRealityPower")) {
                        disCard.upgrade();
                    }

                    if (setCostToZero) {
                        if (disCard.costForTurn > 0) {
                            disCard.costForTurn = 0;
                            disCard.isCostModifiedForTurn = true;
                        }
                        if (disCard.cost > 0) {
                            disCard.cost = 0;
                            disCard.isCostModified = true;
                        }
                    }

                    disCard.current_x = -1000.0F * Settings.xScale;
                    float x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                    float y = (float)Settings.HEIGHT / 2.0F;
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(disCard, x, y, false, true, false));

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> cards = new ArrayList<>();

        while(cards.size() != 3) {
            boolean dupe = false;
            AbstractCard tmp = AbstractDungeon.returnTrulyRandomCardInCombat();

            for (AbstractCard c : cards)
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }

            if (!dupe) {
                cards.add(tmp.makeCopy());
            }
        }

        return cards;
    }
}
