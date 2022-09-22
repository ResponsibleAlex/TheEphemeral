package theEphemeral.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import theEphemeral.EphemeralMod;
import theEphemeral.actions.BlindFuryAction;
import theEphemeral.characters.TheEphemeral;

import static theEphemeral.EphemeralMod.makeCardPath;

@SuppressWarnings("unused")
public class BlindFury extends AbstractDynamicCard {

    // /TEXT DECLARATION/

    public static final String ID = EphemeralMod.makeID(BlindFury.class.getSimpleName());
    public static final String IMG = makeCardPath("BlindFury.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE;

    private static final int COST = 2;
    // private static final int UPGRADED_COST = 0;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DAMAGE = 4;

    // /STAT DECLARATION/


    public BlindFury() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        // Play 1 random revealed attack
        if (canTriggerAttackPlay())
            addToTop(new BlindFuryAction());

        // Damage
        addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        // VFX
        for(int i = 0; i < 5; ++i) {
            addToTop(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
        }
        if (Settings.FAST_MODE) {
            addToTop(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED)));
        } else {
            addToTop(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED), 0.4F));
        }

        // Shuffle draw pile
        megaShuffleTop();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }
}