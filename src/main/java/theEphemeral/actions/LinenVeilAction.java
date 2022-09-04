package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theEphemeral.relics.LinenVeil;
import theEphemeral.variables.TopCardMode;

public class LinenVeilAction extends AbstractGameAction {

    private final AbstractPlayer p;

    public LinenVeilAction() {
        p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (!isDone) {
            TopCardMode oldMode = LinenVeil.Mode;
            TopCardMode newMode = TopCardMode.None;

            if (p.drawPile.size() > 0) {
                AbstractCard c = p.drawPile.getTopCard();
                if (c.type == AbstractCard.CardType.ATTACK) {
                    newMode = TopCardMode.Attack;
                } else if (c.type == AbstractCard.CardType.SKILL) {
                    newMode = TopCardMode.Skill;
                }
            }

            if (newMode != oldMode) {
                // changing modes, subtract old mode and add new mode
                updateValue(oldMode, -1 * LinenVeil.VALUE);
                updateValue(newMode, LinenVeil.VALUE);
            }

            LinenVeil.Mode = newMode;

            for (AbstractGameAction a : AbstractDungeon.actionManager.actions) {
                if (a instanceof LinenVeilAction) {
                    a.isDone = true;
                }
            }
        }

        isDone = true;
    }

    private void updateValue(TopCardMode mode, int value) {
        if (mode == TopCardMode.None)
            return;

        if (mode == TopCardMode.Attack) {
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, value), value));
        } else if (mode == TopCardMode.Skill) {
            addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, value), value));
        }
    }
}
