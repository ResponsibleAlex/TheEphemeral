package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.characters.TheEphemeral;
import theEphemeral.powers.DarkContractPower;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class DarkContract extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(DarkContract.class.getSimpleName());
    public static final String IMG = makeCardPath("DarkContract.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    private static final int EFFECT = 1;

    // /STAT DECLARATION/

    public DarkContract() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = EFFECT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p,
                new DarkContractPower(magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
