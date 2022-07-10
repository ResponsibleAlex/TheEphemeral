package theEphemeral.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import theEphemeral.cards.AbstractVanishingCard;

@SuppressWarnings({"unused"})
@SpirePatch(clz = SingleCardViewPopup.class, method = SpirePatch.CLASS)
public class SingleCardViewPopupPatch {
    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderDescription"
    )
    public static class RenderDescription {
        public static void Prefix(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card) {
            if (___card instanceof AbstractVanishingCard
                    && ___card.misc != ((AbstractVanishingCard) ___card).defaultBaseSecondMagicNumber) {
                ___card.initializeDescription();
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderDescriptionCN"
    )
    public static class RenderDescriptionCN {
        public static void Prefix(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card) {
            if (___card instanceof AbstractVanishingCard
                    && ___card.misc != ((AbstractVanishingCard) ___card).defaultBaseSecondMagicNumber) {
                ___card.initializeDescription();
            }
        }
    }
}
