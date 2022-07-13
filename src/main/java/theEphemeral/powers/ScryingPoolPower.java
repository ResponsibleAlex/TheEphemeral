package theEphemeral.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import theEphemeral.EphemeralMod;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makePowerPath;

public class ScryingPoolPower extends AbstractShufflePower implements CloneablePowerInterface {
    public static final String POWER_ID = EphemeralMod.makeID(ScryingPoolPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ScryingPool84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ScryingPool32.png"));

    public static final int MaxStackAmount = 999;

    public ScryingPoolPower(final int amount) {
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
    public void onShuffle() {
        int drawPileSize = AbstractDungeon.player.drawPile.size();
        int discardPileSize = AbstractDungeon.player.discardPile.size();

        if (drawPileSize > 0 || discardPileSize > 0) {
            flash();

            int amountToDraw = Math.min(amount, drawPileSize + discardPileSize);
            addToBot(new DrawCardAction(amountToDraw));
        }
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
        return new ScryingPoolPower(amount);
    }
}
