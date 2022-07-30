package theEphemeral.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theEphemeral.EphemeralMod;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makePowerPath;

public class DarkContractPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = EphemeralMod.makeID(DarkContractPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("DarkContract84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("DarkContract32.png"));

    public static final int MaxStackAmount = 999;

    public DarkContractPower(final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = amount;
        if (this.amount >= MaxStackAmount) {
            this.amount = MaxStackAmount;
        }

        type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= MaxStackAmount) {
            this.amount = MaxStackAmount;
        }

        updateDescription();
    }

    @Override
    public void onSpecificTrigger() {
        flash();
        addToBot(new LoseHPAction(this.owner, this.owner, this.amount));
        addToBot(new GainEnergyAction(this.amount));
    }

    @Override
    public void updateDescription() {
        StringBuilder energy = new StringBuilder();
        if (amount <= 3) {
            for (int i = 0; i < amount; i++) {
                energy.append("[E] ");
            }
        } else {
            energy.append("#b").append(amount).append(" [E] ");
        }
        description = DESCRIPTIONS[0] + energy + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DarkContractPower(amount);
    }
}
