package theEphemeral.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.city.Ghosts;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theEphemeral.cards.*;

import java.util.ArrayList;

@SuppressWarnings({"unused"})
@SpirePatch(clz = Ghosts.class, method = SpirePatch.CLASS)
public class GhostsPatch {
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("theEphemeral:GhostEventMod");
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final ArrayList<String> cardIds = new ArrayList<>();
    private static final CardGroup spectralCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    @SpirePatch(
            clz = Ghosts.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class GhostsConstructor {
        public static void Postfix(Ghosts __instance) {
            if (AbstractDungeon.player != null &&
                AbstractDungeon.player.chosenClass != null &&
                AbstractDungeon.player.chosenClass.toString().equals("THE_EPHEMERAL")) {

                // add another Refuse to the end
                __instance.imageEventText.setDialogOption(Ghosts.OPTIONS[2]);

                // overwrite the first Refuse slot for our new option
                __instance.imageEventText.updateDialogOption(1, OPTIONS[0]);

                // tell the engine to NOT add extra cards to our reward screen
                __instance.noCardsInRewards = true;
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

                reward();
                AbstractEvent.logMetric("Ghosts", "Spectral card reward (Ephemeral)");

                ___screenNum[0] = 1;

                __instance.imageEventText.updateDialogOption(0, Ghosts.OPTIONS[5]);
                __instance.imageEventText.clearRemainingOptions();

                return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }
    }

    private static void reward() {
        AbstractDungeon.getCurrRoom().rewards.clear();

        RewardItem reward = new RewardItem(AbstractCard.CardColor.COLORLESS);

        // replace cards with Spectral choices
        reward.cards.clear();
        AbstractDungeon.combatRewardScreen.rewards.clear();
        spectralCards.shuffle();

        // add each new card choice
        for (int i = 0; i < numberOfCards() && i < spectralCards.size(); i++) {
            AbstractCard c = spectralCards.group.get(i).makeStatEquivalentCopy();

            // loop relics for this card to call onPreviewObtainCard
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onPreviewObtainCard(c);
            }

            // add to reward
            reward.cards.add(c);
        }

        AbstractDungeon.getCurrRoom().addCardReward(reward);

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.combatRewardScreen.open();
    }

    private static int numberOfCards() {
        if (AbstractDungeon.player == null)
            return 0;

        int numCards = 3; // 3 by default

        for (AbstractRelic r : AbstractDungeon.player.relics) {
            numCards = r.changeNumberOfCardsInReward(numCards);
        }

        if (ModHelper.isModEnabled("Binary")) {
            --numCards;
        }

        return numCards;
    }

    static {
        spectralCards.addToTop(new SpectralBinding());
        spectralCards.addToTop(new SpectralForge());
        spectralCards.addToTop(new SpectralWall());
        spectralCards.addToTop(new SpectralThief());
        spectralCards.addToTop(new SpectralGrasp());
        spectralCards.addToTop(new SpectralPhalanx());
    }
}
