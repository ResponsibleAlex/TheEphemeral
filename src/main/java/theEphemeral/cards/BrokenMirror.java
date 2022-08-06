package theEphemeral.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.actions.BrokenMirrorAction;
import theEphemeral.characters.TheEphemeral;
import theEphemeral.previewWidget.PreviewWidget;

import java.util.List;
import java.util.stream.Collectors;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class BrokenMirror extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(BrokenMirror.class.getSimpleName());
    public static final String IMG = makeCardPath("BrokenMirror.png");
    private static final CardStrings cardStrings;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 0;


    // /STAT DECLARATION/


    public BrokenMirror() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (GetNonBrokenRevealed().size() == 0) {
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }

        return true;
    }

    public static List<AbstractCard> GetNonBrokenRevealed() {
        // get all the Revealed cards EXCEPT other BrokenMirrors
        return PreviewWidget.GetRevealedCards()
                .stream().filter(c -> !(c instanceof BrokenMirror))
                .collect(Collectors.toList());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BrokenMirrorAction());
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            rawDescription = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
