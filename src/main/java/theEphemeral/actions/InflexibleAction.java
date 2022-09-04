package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class InflexibleAction extends AbstractGameAction {

    private final boolean freeToPlayOnce;
    private final AbstractPlayer p;
    private final int energyOnUse;
    private final int block;

    public InflexibleAction(int block, boolean freeToPlayOnce, int energyOnUse) {
        this.p = AbstractDungeon.player;
        this.freeToPlayOnce = freeToPlayOnce;
        duration = Settings.ACTION_DUR_XFAST;
        actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.block = block;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (-1 != energyOnUse) {
            effect = energyOnUse;
        }

        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            p.getRelic(ChemicalX.ID).flash();
        }

        for (int i = 0; i < effect; i++) {
            addToBot(new GainBlockAction(p, p, block));
        }

        if (!freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }

        isDone = true;
    }
}
