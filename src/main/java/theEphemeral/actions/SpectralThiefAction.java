package theEphemeral.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.RipAndTearEffect;

public class SpectralThiefAction extends AbstractGameAction {
    private final AbstractCard card;
    private final int increaseGold;

    public SpectralThiefAction (AbstractCard c, int gold) {
        card = c;
        increaseGold = gold;

        if (Settings.FAST_MODE) {
            startDuration = duration = Settings.ACTION_DUR_FASTER;
        } else {
            startDuration = duration = Settings.ACTION_DUR_FAST;
        }

        source = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

            if (target != null) {
                card.calculateCardDamage((AbstractMonster) target);
                target.damage(new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn));
                addToTop(new VFXAction(new RipAndTearEffect(target.hb.cX, target.hb.cY, Color.RED, Color.GOLD)));

                if ((target.isDying || target.currentHealth <= 0) && !target.halfDead && !target.hasPower("Minion")) {
                    AbstractDungeon.player.gainGold(this.increaseGold);

                    for (int i = 0; i < this.increaseGold; ++i) {
                        AbstractDungeon.effectList.add(new GainPennyEffect(source, target.hb.cX, target.hb.cY, source.hb.cX, source.hb.cY, true));
                    }
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        tickDuration();
    }
}
