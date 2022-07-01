package theEphemeral.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.actions.SpectralForgeAction;
import theEphemeral.characters.TheEphemeral;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class SpectralForge extends AbstractVanishingCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(SpectralForge.class.getSimpleName());
    public static final String IMG = makeCardPath("SpectralForge.png");
    private static final CardStrings cardStrings;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 2;
    private static final int VANISHING = 2;


    // /STAT DECLARATION/


    public SpectralForge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, VANISHING);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);

        if (!canUse) {
            return false;
        } else {
            canUse = p.hand.group.stream().filter(x -> !x.canUpgrade()).toArray().length != p.hand.group.size();

            if (!canUse) {
                cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            }

            return canUse;
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SpectralForgeAction());
        vanish();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            retain = true;
            initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
