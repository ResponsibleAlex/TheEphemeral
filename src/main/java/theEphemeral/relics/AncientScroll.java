package theEphemeral.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.curses.Writhe;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.EphemeralMod;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makeRelicOutlinePath;
import static theEphemeral.EphemeralMod.makeRelicPath;

public class AncientScroll extends CustomRelic {

    // ID, images, text.
    public static final String ID = EphemeralMod.makeID("AncientScroll");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("AncientScroll.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("AncientScroll.png"));

    public AncientScroll() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
        AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(new Writhe()));
        AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(new Writhe()));
    }
    @Override
    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}