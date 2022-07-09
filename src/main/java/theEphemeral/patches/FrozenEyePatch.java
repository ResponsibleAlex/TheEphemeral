package theEphemeral.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.FrozenEye;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

@SuppressWarnings({"unused", "rawtypes"})
@SpirePatch(clz = FrozenEye.class, method = SpirePatch.CLASS)
public class FrozenEyePatch {
    @SpirePatch(
            clz= FrozenEye.class,
            method="getUpdatedDescription"
    )
    public static class GetUpdatedDescription {
        public static SpireReturn Prefix(FrozenEye __instance) {
            if (AbstractDungeon.player != null &&
                AbstractDungeon.player.chosenClass != null &&
                AbstractDungeon.player.chosenClass.toString().equals("THE_EPHEMERAL")) {
                return SpireReturn.Return(languagePack.getRelicStrings("theEphemeral:FrozenEye").DESCRIPTIONS[0]);
            }
            return SpireReturn.Continue();
        }
    }
}
