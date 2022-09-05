package theEphemeral.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theEphemeral.powers.AbstractShufflePower;

@SuppressWarnings("unused")
@SpirePatch(clz = EmptyDeckShuffleAction.class, method = SpirePatch.CLASS)
public class ShuffleEmptyDeckPatch {
    @SpirePatch(
            clz = EmptyDeckShuffleAction.class,
            method = "update"
    )
    public static class EmptyDeckShuffleActionUpdate {
        public static void Postfix(EmptyDeckShuffleAction __instance) {
            if (__instance.isDone) {
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (p instanceof AbstractShufflePower) {
                        ((AbstractShufflePower) p).onShuffle();
                    }
                }
            }
        }
    }
}
