package theEphemeral.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theEphemeral.EphemeralMod;
import theEphemeral.util.TextureLoader;

import static theEphemeral.EphemeralMod.makeRelicOutlinePath;
import static theEphemeral.EphemeralMod.makeRelicPath;

public class GlowingFeather extends CustomRelic {

    // ID, images, text.
    public static final String ID = EphemeralMod.makeID("GlowingFeather");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GlowingFeather.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GlowingFeather.png"));

    public GlowingFeather() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public boolean Active = false;

    private void activate() {
        this.grayscale = false;
        this.beginLongPulse();
        this.Active = true;
        this.counter = 1;
    }
    public void Deactivate() {
        this.grayscale = true;
        this.stopPulse();
        this.Active = false;
        this.counter = -2;
    }


    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.type == AbstractCard.CardType.POWER && !this.grayscale) {
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.Deactivate();
        }
    }

    @Override
    public void obtain() {
        super.obtain();
        this.Deactivate();
    }


    @Override
    public void atBattleStart() {
        this.activate();
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.Deactivate();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}