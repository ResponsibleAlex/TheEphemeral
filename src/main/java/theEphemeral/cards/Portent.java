package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.characters.TheEphemeral;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class Portent extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(Portent.class.getSimpleName());
    public static final String IMG = makeCardPath("Portent.png");
    private static final CardStrings cardStrings;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 0;
    private static final int CARDS_DRAWN = 2;
    private static final int UPGRADE_CARDS_DRAWN = 1;



    // /STAT DECLARATION/


    public Portent() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        fated = true;

        magicNumber = baseMagicNumber = CARDS_DRAWN;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (super.canUse(p, m)) {
            if (willTriggerFated())
                return true;

            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        }
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (triggerFated()) {
            addToBot(new DrawCardAction(magicNumber));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_CARDS_DRAWN);
            initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
