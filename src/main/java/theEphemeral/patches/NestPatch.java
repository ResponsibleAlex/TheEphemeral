package theEphemeral.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.city.Nest;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;

@SuppressWarnings({"unused"})
@SpirePatch(clz = Nest.class, method = SpirePatch.CLASS)
public class NestPatch {
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("theEphemeral:NestEventMod");
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static int curHpLoss;

    @SpirePatch(
            clz = Nest.class,
            method = "buttonEffect"
    )
    public static class ButtonEffect {
        public static SpireReturn<Void> Prefix(Nest __instance, int buttonPressed, @ByRef int[] ___screenNum, int ___goldGain) {

            if (AbstractDungeon.player != null &&
                AbstractDungeon.player.chosenClass != null &&
                AbstractDungeon.player.chosenClass.toString().equals("THE_EPHEMERAL")) {

                if (___screenNum[0] == 0) {

                    __instance.imageEventText.updateBodyText(DESCRIPTIONS[0]);

                    // normal Dagger choice
                    __instance.imageEventText.setDialogOption(Nest.OPTIONS[0] + 6 + Nest.OPTIONS[1], new RitualDagger());

                    // Ambush choice
                    curHpLoss = MathUtils.ceil((float)AbstractDungeon.player.currentHealth * 0.3F);
                    if (curHpLoss >= AbstractDungeon.player.maxHealth) {
                        curHpLoss = AbstractDungeon.player.maxHealth - 1;
                    }

                    __instance.imageEventText.setDialogOption(OPTIONS[0] + ___goldGain + OPTIONS[1] + curHpLoss + OPTIONS[2], new RitualDagger());
                    UnlockTracker.markCardAsSeen("RitualDagger");

                    // Smash and Grab choice
                    __instance.imageEventText.updateDialogOption(0, Nest.OPTIONS[2] + ___goldGain + Nest.OPTIONS[3]);

                    ___screenNum[0] = 1;

                    return SpireReturn.Return();

                } else if (___screenNum[0] == 1) {
                    if (buttonPressed == 2) {

                        // handle Ambush choice
                        // gain dagger
                        AbstractCard c = new RitualDagger();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH * 0.3F, (float) Settings.HEIGHT / 2.0F));

                        // gain gold
                        AbstractDungeon.effectList.add(new RainingGoldEffect(___goldGain));
                        AbstractDungeon.player.gainGold(___goldGain);

                        // lose hp
                        AbstractDungeon.player.damage(new DamageInfo(null, curHpLoss, DamageInfo.DamageType.HP_LOSS));

                        // log
                        ArrayList<String> cardId = new ArrayList<>();
                        cardId.add(c.cardID);
                        AbstractEvent.logMetric("Nest", "Ambushed Cultists (Ephemeral)", cardId, null, null, null, null, null, null, curHpLoss, 0, 0, 0, ___goldGain, 0);

                        // set final screen
                        __instance.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        ___screenNum[0] = 2;
                        __instance.imageEventText.updateDialogOption(0, Nest.OPTIONS[4]);
                        __instance.imageEventText.clearRemainingOptions();

                        return SpireReturn.Return();

                    }
                }
            }

            return SpireReturn.Continue();
        }
    }
}
