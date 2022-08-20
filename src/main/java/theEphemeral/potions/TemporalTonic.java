package theEphemeral.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import theEphemeral.EphemeralMod;
import theEphemeral.actions.PlayTopCardWithCopiesAction;

public class TemporalTonic extends CustomPotion {

    public static final String POTION_ID = EphemeralMod.makeID("TemporalTonic");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    private static final int POTENCY = 3;

    public TemporalTonic() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.BOLT, PotionColor.WHITE);
        isThrown = false;
    }

    public void initializeData() {
        potency = getPotency();

        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        this.tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        addToBot(new PlayTopCardWithCopiesAction(potency - 1));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new TemporalTonic();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return POTENCY;
    }
}
