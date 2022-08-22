package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theEphemeral.cards.Soothsayer;
import theEphemeral.powers.SoothsayerPower;
import theEphemeral.variables.SoothsayerMode;

public class SoothsayerAction extends AbstractGameAction {

    private final AbstractPlayer p;
    private final int pushValue;

    public SoothsayerAction() {
        this(0);
    }

    public SoothsayerAction(int newValue) {
        p = AbstractDungeon.player;
        pushValue = newValue;
    }

    @Override
    public void update() {
        if (!isDone) {
            if (p.hasPower(SoothsayerPower.POWER_ID)) {
                SoothsayerMode oldMode = Soothsayer.Mode;
                SoothsayerMode newMode = SoothsayerMode.None;

                if (p.drawPile.size() > 0) {
                    AbstractCard c = p.drawPile.getTopCard();
                    if (c.type == AbstractCard.CardType.ATTACK) {
                        newMode = SoothsayerMode.Attack;
                    } else if (c.type == AbstractCard.CardType.SKILL) {
                        newMode = SoothsayerMode.Skill;
                    }
                }

                if (newMode != oldMode) {
                    // changing modes, subtract old mode and add new mode
                    int value = p.getPower(SoothsayerPower.POWER_ID).amount;
                    updateValue(oldMode, -1 * value);
                    updateValue(newMode, value);
                } else if (pushValue > 0) {
                    // modes are same and pushing a value, this means playing Soothsayer card and forcing an update
                    updateValue(newMode, pushValue);
                }

                Soothsayer.Mode = newMode;
            }

            for (AbstractGameAction a : AbstractDungeon.actionManager.actions) {
                if (a instanceof SoothsayerAction) {
                    a.isDone = true;
                }
            }
        }

        isDone = true;
    }

    private void updateValue(SoothsayerMode mode, int value) {
        if (mode == SoothsayerMode.None)
            return;

        if (mode == SoothsayerMode.Attack) {
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, value), value));
        } else if (mode == SoothsayerMode.Skill) {
            addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, value), value));
        }
    }
}
