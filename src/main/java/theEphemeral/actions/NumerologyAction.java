package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class NumerologyAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final boolean upgraded;
    private final AbstractPlayer p;
    private final int energyOnUse;

    public NumerologyAction(AbstractPlayer p, boolean upgraded, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }
    @Override
    public void update() {
        int cardsDrawn = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            cardsDrawn = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            cardsDrawn += 2;
            this.p.getRelic("Chemical X").flash();
        }

        int energyDiscount = cardsDrawn;
        if (this.upgraded) {
            ++cardsDrawn;
        }

        if (cardsDrawn > 0) {
            addToTop(new DrawCardWithDiscountAction(cardsDrawn, energyDiscount));

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
