package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.actions.SoothsayerAction;
import theEphemeral.characters.TheEphemeral;
import theEphemeral.powers.SoothsayerPower;
import theEphemeral.variables.SoothsayerMode;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class Soothsayer extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(Soothsayer.class.getSimpleName());
    public static final String IMG = makeCardPath("Soothsayer.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 1;
    private static final int NUMBER = 2;
    private static final int UPGRADE_PLUS_NUMBER = 1;

    public static SoothsayerMode Mode = SoothsayerMode.None;

    // /STAT DECLARATION/

    public Soothsayer() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = NUMBER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p,
                new SoothsayerPower(magicNumber), magicNumber));
        addToBot(new SoothsayerAction(magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_NUMBER);
            initializeDescription();
        }
    }
}
