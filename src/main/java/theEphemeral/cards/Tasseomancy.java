package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.characters.TheEphemeral;
import theEphemeral.previewWidget.PreviewWidget;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class Tasseomancy extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(Tasseomancy.class.getSimpleName());
    public static final String IMG = makeCardPath("Tasseomancy.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 1;
    private static final int AUGURY = 2;
    private static final int UPGRADE_PLUS_AUGURY = 1;


    // /STAT DECLARATION/


    public Tasseomancy() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = AUGURY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        PreviewWidget.AddAugury(magicNumber);

        if (!p.drawPile.isEmpty() && p.drawPile.getTopCard().type == CardType.SKILL) {
            addToBot(new PlayTopCardAction(getRandomMonster(), false));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_AUGURY);
            initializeDescription();
        }
    }
}
