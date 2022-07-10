package theEphemeral.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theEphemeral.EphemeralMod;

@SuppressWarnings("unused")
@SpirePatch(clz = AbstractCreature.class, method = SpirePatch.CLASS)
public class AbstractCreaturePatch {
    @SpirePatch(
            clz= AbstractCreature.class,
            method="applyStartOfTurnPostDrawPowers"
    )
    public static class ApplyStartOfTurnPostDrawPowers
    {
        public static void Postfix(AbstractCreature __instance) {
            if (__instance.isPlayer) {
                EphemeralMod.startOfTurnPostOrbs();
            }
        }
    }
}
