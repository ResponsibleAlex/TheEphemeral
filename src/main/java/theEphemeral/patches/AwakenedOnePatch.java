package theEphemeral.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;

@SuppressWarnings("unused")
@SpirePatch(clz = AwakenedOne.class, method = SpirePatch.CLASS)
public class AwakenedOnePatch {
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("theEphemeral:AwakenedOneDialog");
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;

    @SpirePatch(
            clz= AwakenedOne.class,
            method="usePreBattleAction"
    )
    public static class UsePreBattleAction {
        public static void Postfix(AwakenedOne __instance) {
            if (AbstractDungeon.player != null &&
                AbstractDungeon.player.chosenClass != null &&
                AbstractDungeon.player.chosenClass.toString().equals("THE_EPHEMERAL")) {
                __instance.addToBot(new TalkAction(true, DESCRIPTIONS[0], 1.0F, 4.0F));
            }
        }
    }
}
