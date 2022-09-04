package theEphemeral.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.EphemeralMod;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makeRelicOutlinePath;
import static theEphemeral.EphemeralMod.makeRelicPath;

public class RitualAsh extends CustomRelic {

    // ID, images, text.
    public static final String ID = EphemeralMod.makeID("RitualAsh");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RitualAsh.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RitualAsh.png"));


    public RitualAsh() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (card.isEthereal) {
            flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(
                    new DamageRandomEnemyAction(
                            new DamageInfo(AbstractDungeon.player, 3, DamageInfo.DamageType.THORNS),
                            AbstractGameAction.AttackEffect.FIRE));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}