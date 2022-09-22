package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theEphemeral.EphemeralMod;
import theEphemeral.actions.PlayCardFromDrawPileAction;
import theEphemeral.characters.TheEphemeral;
import theEphemeral.previewWidget.PreviewWidget;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class CastStones extends AbstractDynamicCard {

    // /TEXT DECLARATION/

    public static final String ID = EphemeralMod.makeID(CastStones.class.getSimpleName());
    public static final String IMG = makeCardPath("CastStones.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int AUGURY = 1;

    // /STAT DECLARATION/


    public CastStones() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = AUGURY;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.player.drawPile.isEmpty() && AbstractDungeon.player.drawPile.getTopCard().type == CardType.ATTACK) {
            glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        PreviewWidget.AddAugury(magicNumber);

        if (!p.drawPile.isEmpty()
            && p.drawPile.getTopCard().type == CardType.ATTACK
            && canTriggerAttackPlay()) {
            addToBot(new PlayCardFromDrawPileAction(p.drawPile.getTopCard()));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}