package theEphemeral.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.DrawPileViewScreen;
import theEphemeral.EphemeralMod;

@SuppressWarnings({"unused"})
@SpirePatch(clz = DrawPileViewScreen.class, method = SpirePatch.CLASS)
public class DrawPileViewScreenPatch {
    public static String[] TEXT = CardCrawlGame.languagePack.getPowerStrings("theEphemeral:BlindedViewScreen").DESCRIPTIONS;

    @SpirePatch(
            clz = DrawPileViewScreen.class,
            method = "render"
    )
    public static class Render {
        public static SpireReturn<Void> Prefix(DrawPileViewScreen __instance, SpriteBatch sb) {

            if (AbstractDungeon.player != null &&
                AbstractDungeon.player.chosenClass != null &&
                AbstractDungeon.player.chosenClass.toString().equals("THE_EPHEMERAL") &&
                !EphemeralMod.enableVision) {

                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.DRAW_PILE_BANNER, 0.0F, 0.0F, 630.0F * Settings.scale, 128.0F * Settings.scale);
                FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, TEXT[0], 166.0F * Settings.scale, 82.0F * Settings.scale, Settings.LIGHT_YELLOW_COLOR);
                FontHelper.renderDeckViewTip(sb, TEXT[2], 96.0F * Settings.scale, Settings.CREAM_COLOR);
                AbstractDungeon.overlayMenu.combatDeckPanel.render(sb);

                return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }
    }
}
