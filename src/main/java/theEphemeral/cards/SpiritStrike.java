package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theEphemeral.EphemeralMod;
import theEphemeral.characters.TheEphemeral;
import theEphemeral.vfx.FlashBoonEffect;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class SpiritStrike extends AbstractDynamicCard {

    // /TEXT DECLARATION/

    public static final String ID = EphemeralMod.makeID(SpiritStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("SpiritStrike.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int HITS = 3;
    private static final int UPGRADE_PLUS_HITS = 1;
    private static final int STRENGTH = 2;
    private static final int UPGRADE_PLUS_STRENGTH = 1;

    // /STAT DECLARATION/


    public SpiritStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = HITS;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = STRENGTH;

        tags.add(CardTags.STRIKE);
    }

    @Override
    public void triggerOnManualDiscard() {
        addToBot(new VFXAction(new FlashBoonEffect(this), 0.15F));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, defaultSecondMagicNumber), defaultSecondMagicNumber));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < magicNumber; ++i) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }

        addToBot(new DrawCardAction(1));
        addToBot(new DiscardAction(p, p, 1, false));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_HITS);
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_STRENGTH);
            initializeDescription();
        }
    }
}