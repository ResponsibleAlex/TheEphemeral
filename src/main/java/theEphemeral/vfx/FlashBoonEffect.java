package theEphemeral.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FlashBoonEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 1.0F;
    private static final float FADE_DUR = 0.6F;
    private final AbstractCard card;

    public FlashBoonEffect(AbstractCard cardToDisplay) {
        this.startingDuration = this.duration = EFFECT_DUR;
        this.card = cardToDisplay.makeStatEquivalentCopy();

        this.card.current_x = this.card.target_x = (float)Settings.WIDTH * 0.5F - AbstractCard.IMG_WIDTH;
        this.card.target_y = (float)Settings.HEIGHT / 2.0F;
        this.card.current_y = this.card.target_y - (AbstractCard.IMG_HEIGHT  * 0.25F);

        this.card.transparency = 0.25f;
        this.card.targetTransparency = 1.0f;
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < FADE_DUR) {
            this.card.fadingOut = true;
        }

        this.card.update();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            this.card.render(sb);
        }
    }

    @Override
    public void dispose() {
    }
}
