package theEphemeral.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.actions.SpectralThiefAction;
import theEphemeral.characters.TheEphemeral;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class SpectralThief extends AbstractVanishingCard {

    // /TEXT DECLARATION/

    public static final String ID = EphemeralMod.makeID(SpectralThief.class.getSimpleName());
    public static final String IMG = makeCardPath("SpectralThief.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 1;
    // private static final int UPGRADED_COST = 0;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 5;
    private static final int VANISHING = 4;
    private static final int GOLD_GAINED = 35;
    private static final int UPGRADE_PLUS_GOLD_GAINED = 10;

    // /STAT DECLARATION/


    public SpectralThief() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, VANISHING);
        baseDamage = DAMAGE;

        baseMagicNumber = magicNumber = GOLD_GAINED;
        tags.add(CardTags.HEALING);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SpectralThiefAction(this, magicNumber));
        addToBot(new SpectralThiefAction(this, magicNumber));
        vanish();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_GOLD_GAINED);
            initializeDescription();
        }
    }
}