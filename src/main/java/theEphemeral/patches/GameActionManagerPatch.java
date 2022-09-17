package theEphemeral.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SuppressWarnings("unused")
@SpirePatch(clz = GameActionManager.class, method = SpirePatch.CLASS)
public class GameActionManagerPatch {

    @SpirePatch(
            clz= GameActionManager.class,
            method="cleanCardQueue"
    )
    public static class CleanCardQueuePostfix {
        public static void Postfix(GameActionManager __instance) {
            for (AbstractCard c : AbstractDungeon.player.limbo.group) {
                if (AbstractCardPatch.shouldUnlimbo.get(c)) {
                    c.fadingOut = false;
                }
            }
        }
    }
}
