package theEphemeral.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theEphemeral.EphemeralMod;
import theEphemeral.previewWidget.PreviewWidget;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makeRelicOutlinePath;
import static theEphemeral.EphemeralMod.makeRelicPath;

public class Basin extends CustomRelic {

    // ID, images, text.
    public static final String ID = EphemeralMod.makeID("Basin");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Basin.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Basin.png"));

    public Basin() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        flash();
        PreviewWidget.AddAugury(2);
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public void atTurnStart() {
        flash();
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        PreviewWidget.StartOfTurnIncrease(2);
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(Laurel.ID);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(Laurel.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(Laurel.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r.relicId.equals(ID)) {
                    r.flash();
                }
            }
        } else {
            super.obtain();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}