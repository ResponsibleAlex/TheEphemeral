/*package theEphemeral.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.localization.EventStrings;

@SuppressWarnings({"unused"})
@SpirePatch(clz = Vampires.class, method = SpirePatch.CLASS)
public class VampiresPatch {
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("theEphemeral:VampireEventMod");

    @SpirePatch(
            clz = Vampires.class,
            method = "buttonEffect"
    )
    public static class ButtonEffect {
        public static SpireReturn<Void> Prefix(Vampires __instance, int buttonPressed, int ___screenNum) {

            if (___screenNum == 0) {

                //return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }
    }
}*/
