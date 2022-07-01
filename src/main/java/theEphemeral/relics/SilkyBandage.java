package theEphemeral.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.EphemeralMod;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makeRelicOutlinePath;
import static theEphemeral.EphemeralMod.makeRelicPath;

public class SilkyBandage extends CustomRelic {

    // ID, images, text.
    public static final String ID = EphemeralMod.makeID("SilkyBandage");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SilkyBandage.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SilkyBandage.png"));

    public SilkyBandage() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void onTrigger() {
        flash();
        addToBot(new GainBlockAction(AbstractDungeon.player, 4));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}