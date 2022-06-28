package theEphemeral.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class DrawCardWithDiscountAction extends AbstractGameAction {

    private boolean shuffleCheck;
    private static final Logger logger = LogManager.getLogger(DrawCardWithDiscountAction.class.getName());
    public static ArrayList<AbstractCard> drawnCards = new ArrayList<>();
    private boolean clearDrawHistory;
    private final int discount;

    public DrawCardWithDiscountAction(int amount, int discount) {
        this.shuffleCheck = false;
        this.clearDrawHistory = true;

        this.discount = discount;
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        this.actionType = ActionType.DRAW;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }

    }

    public DrawCardWithDiscountAction(int amount, int discount, boolean clearDrawHistory) {
        this(amount, discount);
        this.clearDrawHistory = clearDrawHistory;
    }

    @Override
    public void update() {
        if (this.clearDrawHistory) {
            this.clearDrawHistory = false;
            drawnCards.clear();
        }

        if (AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("No Draw").flash();
            this.isDone = true;
        } else if (this.amount <= 0) {
            this.isDone = true;
        } else {
            int deckSize = AbstractDungeon.player.drawPile.size();
            int discardSize = AbstractDungeon.player.discardPile.size();
            if (!SoulGroup.isActive()) {
                if (deckSize + discardSize == 0) {
                    this.isDone = true;
                } else if (AbstractDungeon.player.hand.size() == 10) {
                    AbstractDungeon.player.createHandIsFullDialog();
                    this.isDone = true;
                } else {
                    if (!this.shuffleCheck) {
                        int tmp;
                        if (this.amount + AbstractDungeon.player.hand.size() > 10) {
                            tmp = 10 - (this.amount + AbstractDungeon.player.hand.size());
                            this.amount += tmp;
                            AbstractDungeon.player.createHandIsFullDialog();
                        }

                        if (this.amount > deckSize) {
                            tmp = this.amount - deckSize;
                            this.addToTop(new DrawCardWithDiscountAction(tmp, discount, false));
                            this.addToTop(new EmptyDeckShuffleAction());
                            if (deckSize != 0) {
                                this.addToTop(new DrawCardWithDiscountAction(deckSize, discount, false));
                            }

                            this.amount = 0;
                            this.isDone = true;
                            return;
                        }

                        this.shuffleCheck = true;
                    }

                    this.duration -= Gdx.graphics.getDeltaTime();
                    if (this.amount != 0 && this.duration < 0.0F) {
                        if (Settings.FAST_MODE) {
                            this.duration = Settings.ACTION_DUR_XFAST;
                        } else {
                            this.duration = Settings.ACTION_DUR_FASTER;
                        }

                        --this.amount;
                        if (!AbstractDungeon.player.drawPile.isEmpty()) {
                            AbstractCard c = AbstractDungeon.player.drawPile.getTopCard();
                            if (discount > 0 && c.costForTurn > 0) {
                                int newCostForTurn = c.cost - discount;
                                if (newCostForTurn < 0) {
                                    newCostForTurn = 0;
                                }
                                c.costForTurn = newCostForTurn;
                                c.isCostModifiedForTurn = true;
                            }

                            drawnCards.add(c);
                            AbstractDungeon.player.draw();
                            AbstractDungeon.player.hand.refreshHandLayout();
                        } else {
                            logger.warn("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: " + AbstractDungeon.player.masterDeck.getCardNames());
                            this.isDone = true;
                        }

                        if (this.amount == 0) {
                            this.isDone = true;
                        }
                    }

                }
            }
        }
    }
}
