package theEphemeral.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import theEphemeral.EphemeralMod;

@SuppressWarnings("unused")
@SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
public class AbstractPlayerPatch {
    @SpirePatch(
            clz= AbstractPlayer.class,
            method="onVictory"
    )
    public static class OnVictory {
        public static void Prefix(AbstractPlayer __instance) { EphemeralMod.endOfCombat(); }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="preBattlePrep"
    )
    public static class PreBattlePrepPrefix {
        public static void Prefix(AbstractPlayer __instance) { EphemeralMod.preBattlePrepPrefix(); }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="preBattlePrep"
    )
    public static class PreBattlePrepPostfix {
        public static void Postfix(AbstractPlayer __instance) { EphemeralMod.preBattlePrepPostfix(); }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="combatUpdate"
    )
    public static class CombatUpdate {
        public static void Prefix(AbstractPlayer __instance) { EphemeralMod.combatUpdate(); }
    }

    @SpirePatch(
            clz=AbstractPlayer.class,
            method="applyStartOfTurnRelics"
    )
    public static class ApplyStartOfTurnRelics
    {
        public static void Prefix(AbstractPlayer __instance) {
            EphemeralMod.startOfTurn();
        }
    }

    @SpirePatch(
            clz=AbstractPlayer.class,
            method="applyStartOfTurnOrbs"
    )
    public static class ApplyStartOfTurnOrbs
    {
        public static void Postfix(AbstractPlayer __instance) {
            EphemeralMod.startOfTurnPostOrbs();
        }
    }

    @SpirePatch(
            clz=AbstractPlayer.class,
            method="useCard"
    )
    public static class UseCard
    {
        public static void Prefix(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
            if (AbstractCardPatch.shouldUnlimbo.get(c)) {

                AbstractDungeon.player.limbo.removeCard(c);
                if (c.exhaust) {
                    AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                }
            }
        }
    }

    @SpirePatch(
            clz=AbstractPlayer.class,
            method="draw",
            paramtypez = { int.class }
    )
    public static class Draw
    {
        public static void Postfix(AbstractPlayer __instance, int numCards) {
            for (AbstractCard c : __instance.hand.group) {
                c.unfadeOut();
            }
        }
    }
}
