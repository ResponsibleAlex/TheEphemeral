package theEphemeral.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import theEphemeral.EphemeralMod;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makeRelicOutlinePath;
import static theEphemeral.EphemeralMod.makeRelicPath;

public class HookAndYarn extends CustomRelic {

    // ID, images, text.
    public static final String ID = EphemeralMod.makeID("HookAndYarn");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HookAndYarn.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("HookAndYarn.png"));

    private static final int VALUE = 2;

    public HookAndYarn() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onTrigger() {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p, p, new MetallicizePower(p, VALUE), VALUE));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}