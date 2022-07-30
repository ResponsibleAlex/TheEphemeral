package theEphemeral.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import theEphemeral.cards.AbstractVanishingCard;

@SuppressWarnings({"unused"})
@SpirePatch(clz = GridCardSelectScreen.class, method = SpirePatch.CLASS)
public class GridCardSelectScreenPatch {
    @SpirePatch(
            clz = GridCardSelectScreen.class,
            method = "callOnOpen"
    )
    public static class Open {
        public static void Prefix(GridCardSelectScreen __instance) {
            if (__instance.targetGroup != null) {
                for (AbstractCard c : __instance.targetGroup.group) {
                    if (c instanceof AbstractVanishingCard)
                        c.initializeDescription();
                }
            }
        }
    }
}
