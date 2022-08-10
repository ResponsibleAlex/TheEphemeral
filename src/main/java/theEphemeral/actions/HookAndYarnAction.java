package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theEphemeral.relics.HookAndYarn;
import theEphemeral.variables.HookAndYarnMode;

public class HookAndYarnAction extends AbstractGameAction {

    private final AbstractPlayer p;

    public HookAndYarnAction() {
        p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (!isDone) {
            if (p.hasRelic(HookAndYarn.ID)) {
                HookAndYarnMode oldMode = HookAndYarn.Mode;
                HookAndYarnMode newMode = HookAndYarnMode.None;

                if (p.drawPile.size() > 0) {
                    AbstractCard c = p.drawPile.getTopCard();
                    if (c.type == AbstractCard.CardType.ATTACK) {
                        newMode = HookAndYarnMode.Attack;
                    } else if (c.type == AbstractCard.CardType.SKILL) {
                        newMode = HookAndYarnMode.Skill;
                    }
                }

                if (newMode != oldMode) {
                    // changing modes, subtract old mode and add new mode
                    updateValue(oldMode, -1);
                    updateValue(newMode, 1);
                }

                HookAndYarn.Mode = newMode;
            }

            for (AbstractGameAction a : AbstractDungeon.actionManager.actions) {
                if (a instanceof HookAndYarnAction) {
                    a.isDone = true;
                }
            }
        }

        isDone = true;
    }

    private void updateValue(HookAndYarnMode mode, int sign) {
        if (mode == HookAndYarnMode.None)
            return;


        int value = HookAndYarn.VALUE * sign;

        if (mode == HookAndYarnMode.Attack) {
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, value), value));
        } else if (mode == HookAndYarnMode.Skill) {
            addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, value), value));
        }
    }
}
