package theEphemeral.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.SacredBark;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theEphemeral.EphemeralMod;
import theEphemeral.powers.ElysianNectarPower;

public class ElysianNectar extends CustomPotion {

    public static final String POTION_ID = EphemeralMod.makeID("ElysianNectar");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    private static final int POTENCY = 1;

    public ElysianNectar() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.EYE, PotionColor.WHITE);
        isThrown = false;
    }

    public void initializeData() {
        potency = getPotency();

        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(SacredBark.ID)) {
            description = DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0];
        }

        this.tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new ElysianNectarPower(this.potency), this.potency));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ElysianNectar();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return POTENCY;
    }
}
