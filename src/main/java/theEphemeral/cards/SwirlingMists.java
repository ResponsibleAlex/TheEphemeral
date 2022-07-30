package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.characters.TheEphemeral;
import theEphemeral.powers.SwirlingMistsPower;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class SwirlingMists extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(SwirlingMists.class.getSimpleName());
    public static final String IMG = makeCardPath("SwirlingMists.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 1;
    //private static final int UPGRADED_COST = 1;
    private static final int BLOCK = 2;
    private static final int UPGRADE_PLUS_BLOCK = 1;

    // /STAT DECLARATION/

    public SwirlingMists() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p,
                new SwirlingMistsPower(magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
