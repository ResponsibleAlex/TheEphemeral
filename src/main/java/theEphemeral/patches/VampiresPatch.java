package theEphemeral.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theEphemeral.cards.Subsume;

import java.util.ArrayList;

@SuppressWarnings({"unused"})
@SpirePatch(clz = Vampires.class, method = SpirePatch.CLASS)
public class VampiresPatch {
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("theEphemeral:VampireEventMod");
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static int ravenousSlot;
    private static int curHpLoss;
    private static final ArrayList<String> cardIds = new ArrayList<>();

    @SpirePatch(
            clz = Vampires.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class VampiresConstructor {
        public static void Postfix(Vampires __instance) {
            if (AbstractDungeon.player != null &&
                AbstractDungeon.player.chosenClass != null &&
                AbstractDungeon.player.chosenClass.toString().equals("THE_EPHEMERAL")) {

                curHpLoss = MathUtils.ceil((float)AbstractDungeon.player.currentHealth * 0.5F);
                if (curHpLoss >= AbstractDungeon.player.maxHealth) {
                    curHpLoss = AbstractDungeon.player.maxHealth - 1;
                }

                // get index of Refuse option
                ravenousSlot = __instance.imageEventText.optionList.size() - 1;

                // add another Refuse to the end
                __instance.imageEventText.setDialogOption(Vampires.OPTIONS[2]);

                // overwrite the first Refuse slot for our new option
                __instance.imageEventText.updateDialogOption(ravenousSlot, OPTIONS[0] + curHpLoss + OPTIONS[1], new Subsume());
            }
        }
    }

    @SpirePatch(
            clz = Vampires.class,
            method = "buttonEffect"
    )
    public static class ButtonEffect {
        public static SpireReturn<Void> Prefix(Vampires __instance, int buttonPressed, @ByRef int[] ___screenNum) {

            if (___screenNum[0] == 0 &&
                AbstractDungeon.player != null &&
                AbstractDungeon.player.chosenClass != null &&
                AbstractDungeon.player.chosenClass.toString().equals("THE_EPHEMERAL") &&
                buttonPressed == ravenousSlot) {

                CardCrawlGame.sound.play("EVENT_VAMP_BITE");
                __instance.imageEventText.updateBodyText(DESCRIPTIONS[0]);

                AbstractDungeon.player.damage(new DamageInfo(null, curHpLoss, DamageInfo.DamageType.HP_LOSS));
                replaceAttacks();
                AbstractEvent.logMetric("Vampires", "Became ravenous (Ephemeral)", cardIds, null, null, null, null, null, null, curHpLoss, 0, 0, 0, 0, 0);

                ___screenNum[0] = 1;

                __instance.imageEventText.updateDialogOption(0, Vampires.OPTIONS[5]);
                __instance.imageEventText.clearRemainingOptions();

                return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }
    }

    private static void replaceAttacks() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;

        int i;
        for(i = masterDeck.size() - 1; i >= 0; --i) {
            AbstractCard card = masterDeck.get(i);
            if (card.tags.contains(AbstractCard.CardTags.STARTER_STRIKE)) {
                AbstractDungeon.player.masterDeck.removeCard(card);
            }
        }

        for(i = 0; i < 2; ++i) {
            AbstractCard c = new Subsume();
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            cardIds.add(c.cardID);
        }
    }
}
