package theEphemeral.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theEphemeral.EphemeralMod;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makePowerPath;

public class MaelstromPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = EphemeralMod.makeID(MaelstromPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Maelstrom84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Maelstrom32.png"));

    public static final int MaxStackAmount = 999;
    private int amountOfWeakAndVulnerable;

    public MaelstromPower(final int amountOfWeakAndVulnerable) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = 5;
        this.amountOfWeakAndVulnerable = amountOfWeakAndVulnerable;
        type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.amountOfWeakAndVulnerable += stackAmount;
        if (this.amountOfWeakAndVulnerable >= MaxStackAmount) {
            this.amountOfWeakAndVulnerable = MaxStackAmount;
        }

        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        --this.amount;
        if (this.amount == 0) {
            this.flash();
            this.amount = 5;
            applyWeakAndVulnerable();
        }

        this.updateDescription();
    }

    private void applyWeakAndVulnerable() {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, this.amountOfWeakAndVulnerable, false), this.amountOfWeakAndVulnerable, true, AbstractGameAction.AttackEffect.NONE));
            this.addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.amountOfWeakAndVulnerable, false), this.amountOfWeakAndVulnerable, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    public void atStartOfTurn() {
        this.amount = 5;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amountOfWeakAndVulnerable + DESCRIPTIONS[3] + amountOfWeakAndVulnerable + DESCRIPTIONS[4];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + amountOfWeakAndVulnerable + DESCRIPTIONS[3] + amountOfWeakAndVulnerable + DESCRIPTIONS[4];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new MaelstromPower(amount);
    }
}
