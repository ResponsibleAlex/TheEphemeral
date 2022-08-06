package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.characters.TheEphemeral;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class SpectralForge extends AbstractVanishingCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(SpectralForge.class.getSimpleName());
    public static final String IMG = makeCardPath("SpectralForge.png");
    private static final CardStrings cardStrings;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 1;
    //private static final int UPGRADED_COST = 0;
    private static final int VANISHING = 2;


    // /STAT DECLARATION/


    public SpectralForge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, VANISHING);
        //tags.add(CardTags.HEALING);
    }

    public static boolean CanUpgradeInDeck(AbstractCard card) {
        for (AbstractCard masterDeckCard : AbstractDungeon.player.masterDeck.group) {
            if (card.uuid == masterDeckCard.uuid)
                return masterDeckCard.canUpgrade();
        }

        return false;
    }

    /*@Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);

        if (!canUse) {
            return false;
        } else {
            canUse = false; // default to false unless we find an upgradeable card
            for (AbstractCard c : p.hand.group) {
                if (c.uuid != this.uuid && CanUpgradeInDeck(c)) {
                    canUse = true;
                    break;
                }
            }

            if (!canUse) {
                cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            }

            return canUse;
        }
    }*/

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApotheosisAction());
        vanish();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            addVanish();
            isInnate = true;
            rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    static {
        cardStrings = languagePack.getCardStrings(ID);
    }
}
