package theEphemeral.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.relics.GlowingFeather;

@SuppressWarnings({"unused", "rawtypes"})
@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class AbstractCardPatch {
    @SpirePatch(
            clz = AbstractCard.class,
            method = "freeToPlay"
    )
    public static class FreeToPlay {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player != null &&
                AbstractDungeon.player.hasRelic(GlowingFeather.ID)
                    && !AbstractDungeon.player.getRelic(GlowingFeather.ID).grayscale
                    && __instance.type == AbstractCard.CardType.POWER) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }
}
