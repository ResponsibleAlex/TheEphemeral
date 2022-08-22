package theEphemeral.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.relics.CultistMask;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

@SuppressWarnings({"unused", "rawtypes"})
@SpirePatch(clz = CultistMask.class, method = SpirePatch.CLASS)
public class CultistMaskPatch {
    @SpirePatch(
            clz= CultistMask.class,
            method="atBattleStart"
    )
    public static class AtBattleStart {
        public static void Postfix(CultistMask __instance) {
            AbstractPlayer p = AbstractDungeon.player;
            if (p.chosenClass.toString().equals("THE_EPHEMERAL")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RitualPower(p, 1, true), 1));
            }
        }
    }

    @SpirePatch(
            clz= CultistMask.class,
            method="getUpdatedDescription"
    )
    public static class GetUpdatedDescription {
        public static SpireReturn Prefix(CultistMask __instance) {
            if (AbstractDungeon.player != null &&
                    AbstractDungeon.player.chosenClass != null &&
                    AbstractDungeon.player.chosenClass.toString().equals("THE_EPHEMERAL")) {
                return SpireReturn.Return(languagePack.getRelicStrings("theEphemeral:CultistMask").DESCRIPTIONS[0]);
            }
            return SpireReturn.Continue();
        }
    }
}
