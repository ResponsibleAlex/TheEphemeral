package theEphemeral.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.OverlayMenu;
import theEphemeral.EphemeralMod;

@SuppressWarnings("unused")
@SpirePatch(clz = OverlayMenu.class, method = SpirePatch.CLASS)
public class OverlayMenuPatch {
    @SpirePatch(
            clz= OverlayMenu.class,
            method="render"
    )
    public static class Render {
        public static void Prefix(OverlayMenu __instance, SpriteBatch sb) {
            EphemeralMod.renderPreviewWidget(sb);
        }
    }
}
