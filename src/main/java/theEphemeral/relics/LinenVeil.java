package theEphemeral.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theEphemeral.EphemeralMod;
import theEphemeral.util.TextureLoader;
import theEphemeral.variables.TopCardMode;

import static theEphemeral.EphemeralMod.makeRelicOutlinePath;
import static theEphemeral.EphemeralMod.makeRelicPath;

public class LinenVeil extends CustomRelic {

    // ID, images, text.
    public static final String ID = EphemeralMod.makeID("LinenVeil");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("LinenVeil.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("LinenVeil.png"));
    public static TopCardMode Mode = TopCardMode.None;
    public static final int VALUE = 2;

    public LinenVeil() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}