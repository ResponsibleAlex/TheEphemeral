package theEphemeral.powers;

import theEphemeral.EphemeralMod;
import theEphemeral.previewWidget.PreviewWidget;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makePowerPath;

public class AuguryPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = EphemeralMod.makeID(AuguryPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Augury84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Augury32.png"));

    public AuguryPower(final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = amount;
        if (this.amount >= 7) {
            this.amount = 7;
        }

        type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updatePower();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 7) {
            this.amount = 7;
        }

        updatePower();
    }

    @Override
    public void atStartOfTurn() {
        amount -= 2;

        if (amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
        } else {
            updatePower();
        }
    }

    private void updatePower() {
        updateDescription();
        PreviewWidget.Reset(this.amount);
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new AuguryPower(amount);
    }
}
