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
import com.megacrit.cardcrawl.events.city.Ghosts;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theEphemeral.cards.GrimResolve;

import java.util.ArrayList;

@SuppressWarnings({"unused"})
@SpirePatch(clz = Ghosts.class, method = SpirePatch.CLASS)
public class GhostsPatch {
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("theEphemeral:GhostEventMod");
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static int curHpLoss;
    private static final ArrayList<String> cardIds = new ArrayList<>();

    @SpirePatch(
            clz = Ghosts.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class GhostsConstructor {
        public static void Postfix(Ghosts __instance) {
            if (AbstractDungeon.player != null &&
                AbstractDungeon.player.chosenClass != null &&
                AbstractDungeon.player.chosenClass.toString().equals("THE_EPHEMERAL")) {

                curHpLoss = MathUtils.ceil((float)AbstractDungeon.player.currentHealth * 0.15F);
                if (curHpLoss >= AbstractDungeon.player.maxHealth) {
                    curHpLoss = AbstractDungeon.player.maxHealth - 1;
                }

                // add another Refuse to the end
                __instance.imageEventText.setDialogOption(Ghosts.OPTIONS[2]);

                // overwrite the first Refuse slot for our new option
                __instance.imageEventText.updateDialogOption(1, OPTIONS[0] + curHpLoss + OPTIONS[1], new GrimResolve());
            }
        }
    }

    @SpirePatch(
            clz = Ghosts.class,
            method = "buttonEffect"
    )
    public static class ButtonEffect {
        public static SpireReturn<Void> Prefix(Ghosts __instance, int buttonPressed, @ByRef int[] ___screenNum) {

            if (___screenNum[0] == 0 &&
                AbstractDungeon.player != null &&
                AbstractDungeon.player.chosenClass != null &&
                AbstractDungeon.player.chosenClass.toString().equals("THE_EPHEMERAL") &&
                buttonPressed == 1) {

                CardCrawlGame.sound.play("ATTACK_PIERCING_WAIL");
                __instance.imageEventText.updateBodyText(DESCRIPTIONS[0]);

                AbstractDungeon.player.damage(new DamageInfo(null, curHpLoss, DamageInfo.DamageType.HP_LOSS));
                gainResolve();
                AbstractEvent.logMetric("Ghosts", "Gained Resolve (Ephemeral)", cardIds, null, null, null, null, null, null, curHpLoss, 0, 0, 0, 0, 0);

                ___screenNum[0] = 1;

                __instance.imageEventText.updateDialogOption(0, Ghosts.OPTIONS[5]);
                __instance.imageEventText.clearRemainingOptions();

                return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }
    }

    private static void gainResolve() {
        AbstractCard c = new GrimResolve();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        cardIds.add(c.cardID);
    }
}
