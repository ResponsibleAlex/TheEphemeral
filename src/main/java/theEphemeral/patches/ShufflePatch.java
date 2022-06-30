package theEphemeral.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theEphemeral.powers.AbstractShufflePower;

@SuppressWarnings("unused")
@SpirePatch(clz = ShuffleAction.class, method = SpirePatch.CLASS)
public class ShufflePatch {
    @SpirePatch(
            clz= ShuffleAction.class,
            method="update"
    )
    public static class Update {
        public static void Prefix(ShuffleAction __instance, boolean ___triggerRelics) {
            if (___triggerRelics) {
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (p instanceof AbstractShufflePower) {
                        ((AbstractShufflePower) p).onShuffle();
                    }
                }
            }
        }
    }
}
