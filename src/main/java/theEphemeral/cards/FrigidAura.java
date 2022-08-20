package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theEphemeral.EphemeralMod;
import theEphemeral.characters.TheEphemeral;
import theEphemeral.vfx.FrostEffect;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class FrigidAura extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(FrigidAura.class.getSimpleName());
    public static final String IMG = makeCardPath("FrigidAura.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 2;
    private static final int BLOCK = 12;
    private static final int UPGRADE_PLUS_BLOCK = 4;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 1;


    // /STAT DECLARATION/


    public FrigidAura() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;

        fated = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));

        if (triggerFated()) {
            int amount = magicNumber + (magicNumber * soothsayer());

            AbstractDungeon.getCurrRoom().monsters.monsters.forEach(mo -> {
                if (!mo.isDeadOrEscaped()) {
                    addToBot(new VFXAction(new FrostEffect(mo.hb.cX, mo.hb.cY)));
                    addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, amount, false), amount, true, AbstractGameAction.AttackEffect.NONE));
                }
            });
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
