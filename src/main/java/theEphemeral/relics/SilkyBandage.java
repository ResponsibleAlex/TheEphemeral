package theEphemeral.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theEphemeral.EphemeralMod;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makeRelicOutlinePath;
import static theEphemeral.EphemeralMod.makeRelicPath;

public class SilkyBandage extends CustomRelic {

    // ID, images, text.
    public static final String ID = EphemeralMod.makeID("SilkyBandage");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SilkyBandage.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SilkyBandage.png"));

    public static final int VALUE = 2;

    public SilkyBandage() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}