package theEphemeral.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.EphemeralMod;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makeRelicOutlinePath;
import static theEphemeral.EphemeralMod.makeRelicPath;

public class StoneTablet extends CustomRelic {

    // ID, images, text.
    public static final String ID = EphemeralMod.makeID("StoneTablet");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("StoneTablet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("StoneTablet.png"));

    private final static int DAMAGE = 6;

    public StoneTablet() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.HEAVY);
    }

    @Override
    public void onShuffle() {
        flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(DAMAGE, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}