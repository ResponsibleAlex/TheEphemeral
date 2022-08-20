package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import theEphemeral.EphemeralMod;
import theEphemeral.characters.TheEphemeral;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class Dissipate extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(Dissipate.class.getSimpleName());
    public static final String IMG = makeCardPath("Dissipate.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;
    private static final int BLOCK = 24;


    // /STAT DECLARATION/


    public Dissipate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;

        fated = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));

        if (triggerFated()) {
            int amount = 1 + soothsayer();
            addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, amount), amount));
            exhaust = true;
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
