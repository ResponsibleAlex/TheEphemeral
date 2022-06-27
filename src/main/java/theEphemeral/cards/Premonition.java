package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.characters.TheEphemeral;
import theEphemeral.powers.AuguryPower;

import static theEphemeral.EphemeralMod.makeCardPath;

public class Premonition extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(Premonition.class.getSimpleName());
    public static final String IMG = makeCardPath("Premonition.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 1;
    // private static final int UPGRADED_COST = 0;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_NUMBER = 1;


    // /STAT DECLARATION/


    public Premonition() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;

        fated = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new ApplyPowerAction(p, p, new AuguryPower(magicNumber), magicNumber));

        if (triggerFated()) {
            addToBot(new GainEnergyAction(1));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            //upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
