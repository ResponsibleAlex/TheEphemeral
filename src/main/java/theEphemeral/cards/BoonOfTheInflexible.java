package theEphemeral.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.actions.InflexibleAction;
import theEphemeral.actions.PlayCardFromDiscardPileAction;
import theEphemeral.characters.TheEphemeral;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class BoonOfTheInflexible extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(BoonOfTheInflexible.class.getSimpleName());
    public static final String IMG = makeCardPath("BoonOfTheInflexible.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = -1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private static final int FATED_BLOCK = 2;


    // /STAT DECLARATION/


    public BoonOfTheInflexible() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        magicNumber = baseMagicNumber = FATED_BLOCK;
    }

    @Override
    public void triggerOnManualDiscard() {
        applyPowers();
        addToBot(new PlayCardFromDiscardPileAction(this));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new InflexibleAction(block, freeToPlayOnce, energyOnUse));
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
