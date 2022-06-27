package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.characters.TheEphemeral;
import theEphemeral.previewWidget.PreviewWidget;

import java.util.ArrayList;

import static theEphemeral.EphemeralMod.makeCardPath;

public class PeerThroughMists extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(PeerThroughMists.class.getSimpleName());
    public static final String IMG = makeCardPath("PeerThroughMists.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 1;
    // private static final int UPGRADED_COST = 0;
    private static final int BLOCK = 3;
    private static final int UPGRADE_PLUS_BLOCK = 1;


    // /STAT DECLARATION/


    public PeerThroughMists() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> handCardsToRemove = new ArrayList<>();
        p.hand.group.forEach(x -> {
            if (x.type == CardType.CURSE || x.type == CardType.STATUS) {
                handCardsToRemove.add(x);
            }
        });
        handCardsToRemove.forEach(x -> {
            p.hand.moveToExhaustPile(x);
            addToBot(new GainBlockAction(p, p, block));
        });

        ArrayList<AbstractCard> drawPileCardsToRemove = new ArrayList<>();
        int revealedIndex = PreviewWidget.revealed - 1;
        p.drawPile.group.subList(0, revealedIndex).forEach(x -> {
            if (x.type == CardType.CURSE || x.type == CardType.STATUS) {
                drawPileCardsToRemove.add(x);
            }
        });
        drawPileCardsToRemove.forEach(x -> {
            p.drawPile.moveToExhaustPile(x);
            addToBot(new GainBlockAction(p, p, block));
        });
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
