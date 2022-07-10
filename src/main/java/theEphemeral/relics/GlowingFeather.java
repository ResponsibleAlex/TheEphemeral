package theEphemeral.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.type == AbstractCard.CardType.POWER && this.counter > 0) {
            this.counter--;
            flash();
        }
    }

    @Override
    public void atBattleStart() {
        this.counter = 1;
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.counter = -1;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}