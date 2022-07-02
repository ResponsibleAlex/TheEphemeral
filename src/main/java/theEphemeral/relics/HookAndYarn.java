package theEphemeral.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.EphemeralMod;
import theEphemeral.previewWidget.PreviewWidget;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makeRelicOutlinePath;
import static theEphemeral.EphemeralMod.makeRelicPath;

public class HookAndYarn extends CustomRelic {

    // ID, images, text.
    public static final String ID = EphemeralMod.makeID("HookAndYarn");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HookAndYarn.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("HookAndYarn.png"));

    public HookAndYarn() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        if (PreviewWidget.GetAugury() > 0) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 6));
        }

    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}