package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.characters.TheEphemeral;
import theEphemeral.fleetingCards.FleetingThought;
import theEphemeral.previewWidget.PreviewWidget;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class Dowse extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(Dowse.class.getSimpleName());
    public static final String IMG = makeCardPath("Dowse.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 1;
    private static final int AUGURY = 2;


    // /STAT DECLARATION/


    public Dowse() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;

        cardsToPreview = new FleetingThought();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        PreviewWidget.AddAugury(AUGURY);
        addToBot(new MakeTempCardInHandAction(new FleetingThought(), magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
