package theEphemeral.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import theEphemeral.cards.AbstractVanishingCard;

@SuppressWarnings({"unused"})
@SpirePatch(clz = MasterDeckViewScreen.class, method = SpirePatch.CLASS)
public class MasterDeckViewScreenPatch {
    @SpirePatch(
            clz = MasterDeckViewScreen.class,
            method = "open"
    )
    public static class Open {
        public static void Postfix(MasterDeckViewScreen __instance) {
            if (AbstractDungeon.player != null
                && AbstractDungeon.player.masterDeck != null) {
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c instanceof AbstractVanishingCard)
                        c.initializeDescription();
                }
            }
        }
    }
}
