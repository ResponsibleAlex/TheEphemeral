package theEphemeral.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

@SuppressWarnings("unused")
@SpirePatch(clz = ShowCardBrieflyEffect.class, method = SpirePatch.CLASS)
public class ShowCardBrieflyEffectPatch {

    @SpirePatch(
            clz = ShowCardBrieflyEffect.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez={
                    AbstractCard.class
            }
    )
    public static class ShowCardBrieflyEffectConstructor1 {
        public static void Postfix(ShowCardBrieflyEffect __instance, AbstractCard card, AbstractCard ___card) {
            ___card.initializeDescription();
        }
    }

    @SpirePatch(
            clz = ShowCardBrieflyEffect.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez={
                    AbstractCard.class,
                    float.class,
                    float.class
            }
    )
    public static class ShowCardBrieflyEffectConstructor2 {
        public static void Postfix(ShowCardBrieflyEffect __instance, AbstractCard card, float x, float y, AbstractCard ___card) {
            ___card.initializeDescription();
        }
    }
}
