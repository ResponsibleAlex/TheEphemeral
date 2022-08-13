package theEphemeral.potions;

import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theEphemeral.EphemeralMod;
import theEphemeral.previewWidget.PreviewWidget;

public class PotionOfProphecy extends CustomPotion {

    public static final String POTION_ID = EphemeralMod.makeID("PotionOfProphecy");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final String AUGURY_KEYWORD = EphemeralMod.getModID().toLowerCase() + ":Augury";
    public static final String AUGURY_NAME = BaseMod.getKeywordProper(AUGURY_KEYWORD);
    public static final String AUGURY_DESCRIPTION = BaseMod.getKeywordDescription(AUGURY_KEYWORD);

    private static final int POTENCY = 4;

    public PotionOfProphecy() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.M, PotionColor.WHITE);
        isThrown = false;
    }

    public void initializeData() {
        potency = getPotency();

        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        this.tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(AUGURY_NAME, AUGURY_DESCRIPTION));
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            PreviewWidget.AddAugury(potency);
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new PotionOfProphecy();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return POTENCY;
    }
}
