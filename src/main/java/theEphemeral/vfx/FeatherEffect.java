package theEphemeral.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static theEphemeral.EphemeralMod.makeEffectPath;

public class FeatherEffect extends AbstractGameEffect {
    private static final Texture img = ImageMaster.loadImage(makeEffectPath("feather.png"));

    private final float start_x;
    private float x_mod;
    private float y;
    private final float start_y;
    private final float end_y;
    private float y_mod;
    private final boolean flip;

    public FeatherEffect(float x, float y) {
        this.flip = MathUtils.randomBoolean();

        this.start_x = x + MathUtils.random(-55.0f, 55.0f);
        this.x_mod = 0;

        this.start_y = this.y = y - 20.0f;
        this.end_y = this.start_y - 480.0f;
        this.y_mod = 0;

        renderBehind = true;

        color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        scale = MathUtils.random(0.75F, 1.25F) * Settings.scale;
        startingDuration = duration = 4.0f;

        updateValues();
    }

    private void updateValues() {
        rotation = Interpolation.sine.apply(0.0f, 90.0f, duration / (startingDuration / 8.0f));
        x_mod = Interpolation.sine.apply(-26.0f, 26.0f, duration / (startingDuration / 8.0f));
        y = Interpolation.linear.apply(end_y, start_y, duration / startingDuration);
        y_mod = Interpolation.sine.apply(0.0f, 26.0f, duration / (startingDuration / 8.0f));
    }

    @Override
    public void update() {
        // tick duration, and fade out during second half of duration
        super.update();

        updateValues();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.draw(img,
                Settings.scale * (start_x + x_mod),
                Settings.scale * (y + y_mod),
                26.0F, 0.0F, 26.0F, 26.0F,
                scale, scale, rotation,
                0, 0, 26, 26, flip, flip);
    }

    @Override
    public void dispose() {
    }
}
