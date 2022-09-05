package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.characters.TheEphemeral;
import theEphemeral.previewWidget.PreviewWidget;

import static theEphemeral.EphemeralMod.makeCardPath;

public class ReadTheStars extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(ReadTheStars.class.getSimpleName());
    public static final String IMG = makeCardPath("ReadTheStars.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 1;
    // private static final int UPGRADED_COST = 0;
    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int AUGURY = 2;
    //private static final int UPGRADE_PLUS_AUGURY = 1;


    // /STAT DECLARATION/


    public ReadTheStars() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = AUGURY;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        PreviewWidget.AddAugury(magicNumber);
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            //upgradeMagicNumber(UPGRADE_PLUS_AUGURY);
            initializeDescription();
        }
    }
}
