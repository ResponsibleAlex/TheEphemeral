package theEphemeral.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.buttons.EndTurnButton;
import theEphemeral.EphemeralMod;

@SuppressWarnings("unused")
@SpirePatch(clz = EndTurnButton.class, method = SpirePatch.CLASS)
public class EndTurnButtonPatch {
    @SpirePatch(
            clz= EndTurnButton.class,
            method="disable",
            paramtypez={
                    boolean.class
            }
    )
    public static class Disable {
        public static void Prefix(EndTurnButton __instance, boolean isEnemyTurn) {
            // If the button is changing from enabled true, we are sure we are at the end of player turn.
            // Prevents Time Eater / HarbingerForm nonsense.
            if (__instance.enabled)
                EphemeralMod.atEndOfTurn();
        }
    }
}
