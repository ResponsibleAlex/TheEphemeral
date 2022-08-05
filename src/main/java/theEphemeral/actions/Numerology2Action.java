package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class Numerology2Action extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final boolean upgraded;
    private final AbstractPlayer p;
    private final int energyOnUse;

    public Numerology2Action(AbstractPlayer p, boolean upgraded, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }
    @Override
    public void update() {
        int targetCost = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            targetCost = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            targetCost += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (this.upgraded) {
            ++targetCost;
        }

        if (targetCost > 0) {
            addToTop(new PlayTopmostCardWithCostXAction(targetCost));

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
