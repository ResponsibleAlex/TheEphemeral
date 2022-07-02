package theEphemeral.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.EphemeralMod;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makeRelicOutlinePath;
import static theEphemeral.EphemeralMod.makeRelicPath;

public class Pomegranate extends CustomRelic {

    // ID, images, text.
    public static final String ID = EphemeralMod.makeID("Pomegranate");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Pomegranate.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Pomegranate.png"));

    public Pomegranate() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(21, true);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}