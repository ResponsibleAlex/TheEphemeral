package theEphemeral.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.EphemeralMod;
import theEphemeral.previewWidget.PreviewWidget;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makeRelicOutlinePath;
import static theEphemeral.EphemeralMod.makeRelicPath;

public class Laurel extends CustomRelic {

    // ID, images, text.
    public static final String ID = EphemeralMod.makeID("Laurel");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Laurel.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Laurel.png"));

    public Laurel() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        PreviewWidget.AddAugury(2);
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}