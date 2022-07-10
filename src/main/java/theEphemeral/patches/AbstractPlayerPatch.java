package theEphemeral.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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
    public static class PreBattlePrep {
        public static void Prefix(AbstractPlayer __instance) { EphemeralMod.startOfCombat(); }
    }

    @SpirePatch(
            clz= AbstractPlayer.class,
            method="combatUpdate"
    )
    public static class CombatUpdate {
        public static void Prefix(AbstractPlayer __instance) { EphemeralMod.updatePreviewWidget(); }
    }

    @SpirePatch(
            clz=AbstractPlayer.class,
            method="render"
    )
    public static class RenderPreviewWidget
    {
        public static void Postfix(AbstractPlayer __instance, SpriteBatch sb) {
            EphemeralMod.renderPreviewWidget(sb);
        }
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
}
