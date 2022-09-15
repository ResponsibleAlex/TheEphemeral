package theEphemeral.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import theEphemeral.EphemeralMod;
import theEphemeral.characters.TheEphemeral;
import theEphemeral.vfx.FlashBoonEffect;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class BoonOfTheInflexible extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = EphemeralMod.makeID(BoonOfTheInflexible.class.getSimpleName());
    public static final String IMG = makeCardPath("BoonOfTheInflexible.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 0;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 1;


    // /STAT DECLARATION/


    public BoonOfTheInflexible() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void triggerOnManualDiscard() {
        MonsterGroup monsters = AbstractDungeon.getMonsters();

        if (!monsters.areMonstersBasicallyDead()) {
            AbstractPlayer p = AbstractDungeon.player;

            this.addToBot(new VFXAction(new FlashBoonEffect(this), 0.15F));
            this.addToBot(new SFXAction("ATTACK_HEAVY"));
            this.addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));

            for (AbstractMonster m : monsters.monsters) {
                this.addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -this.magicNumber), -this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                if (!m.hasPower("Artifact")) {
                    this.addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                }
            }
        }

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1));
        addToBot(new DiscardAction(p, p, 1, false));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
