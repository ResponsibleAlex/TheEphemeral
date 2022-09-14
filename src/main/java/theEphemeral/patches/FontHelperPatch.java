package theEphemeral.patches;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.helpers.FontHelper;
import theEphemeral.cards.BoonOfTheAlloter;
import theEphemeral.cards.BoonOfTheInflexible;
import theEphemeral.cards.BoonOfTheSpinner;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

@SuppressWarnings("unused")
@SpirePatch2(clz = FontHelper.class, method = SpirePatch.CLASS)
public class FontHelperPatch {

    private final static String SPINNER_NAME = languagePack.getCardStrings(BoonOfTheSpinner.ID).NAME;
    private final static String ALLOTER_NAME = languagePack.getCardStrings(BoonOfTheAlloter.ID).NAME;
    private final static String INFLEXIBLE_NAME = languagePack.getCardStrings(BoonOfTheInflexible.ID).NAME;

    @SpirePatch2(
            clz= FontHelper.class,
            method="renderRotatedText"
    )
    public static class RenderRotatedText {
        public static void Prefix(BitmapFont font, String msg) {
            if (msg.contains(SPINNER_NAME)
                    || msg.contains(ALLOTER_NAME)
                    || msg.contains(INFLEXIBLE_NAME)) {
                BitmapFont.BitmapFontData fontData = font.getData();
                fontData.scaleX *= 0.9f;
            }
        }
    }
}
