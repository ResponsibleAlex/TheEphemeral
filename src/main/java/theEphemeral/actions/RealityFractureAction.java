package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import java.util.ArrayList;
import java.util.Collections;

public class RealityFractureAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final int damage;
    private final AbstractPlayer p;
    private final AbstractMonster m;
    private final DamageInfo.DamageType damageTypeForTurn;
    private final int energyOnUse;
    private final int magicNumber;

    public RealityFractureAction(AbstractPlayer p, AbstractMonster m, int damage, DamageInfo.DamageType damageTypeForTurn, boolean freeToPlayOnce, int energyOnUse, int magicNumber) {
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.freeToPlayOnce = freeToPlayOnce;
        this.actionType = ActionType.SPECIAL;
        this.damageTypeForTurn = damageTypeForTurn;
        this.energyOnUse = energyOnUse;
        this.magicNumber = magicNumber;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        effect += magicNumber;

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {
                this.addToBot(new DamageAction(this.m, new DamageInfo(this.p, this.damage, this.damageTypeForTurn), getEffect(i, effect)));
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }

    private AttackEffect getEffect(int i, int effect) {
        if (i == effect - 1) {
            this.addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
            return AttackEffect.NONE;
        } else {
            Collections.shuffle(attackEffects);
            return attackEffects.get(0);
        }
    }

    private static final ArrayList<AttackEffect> attackEffects = new ArrayList<>();
    static {
        attackEffects.add(AttackEffect.SLASH_HORIZONTAL);
        attackEffects.add(AttackEffect.SLASH_DIAGONAL);
        attackEffects.add(AttackEffect.SLASH_VERTICAL);
    }
}
