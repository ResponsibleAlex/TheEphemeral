package theEphemeral.previewWidget;

import theEphemeral.powers.AuguryPower;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class PreviewWidget {
    // CardGroup that is actually getting displayed
    private static final CardGroup previews = new CardGroup(CardGroupType.UNSPECIFIED);

    // clone of DrawPile to compare for updates
    private static final CardGroup drawPileCopy = new CardGroup(CardGroupType.UNSPECIFIED);

    private static final float drawScale = 0.5f;
    public static int revealed = 0;

    public static void Reset() {
        Reset(0);
    }
    public static void Reset(int amount) {
        CardGroup drawPile = AbstractDungeon.player.drawPile;

        // only show the amount that actually exist
        revealed = Math.min(amount, drawPile.size());

        // clear old previews
        previews.clear();

        // add copies to the CardGroup and initialize visuals
        if (revealed > 0) {
            for (int i = revealed - 1; i >= 0; i--) {
                AbstractCard c = drawPile.group.get(i);
                AbstractCard cpy = c.makeStatEquivalentCopy();

                cpy.setAngle(0.0F, true);
                cpy.current_x = cpy.target_x = Settings.scale * 20;//AbstractCard.IMG_WIDTH;
                cpy.current_y = cpy.target_y = Settings.scale * (Settings.HEIGHT * 0.75f + (i * 40)) ;
                cpy.targetDrawScale = drawScale;
                cpy.lighten(true);

                previews.addToTop(cpy);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void Update() {
        // check to see if the draw pile has changed since last update
        CardGroup drawPile = AbstractDungeon.player.drawPile;
        if (!drawPile.group.equals(drawPileCopy.group)) {
            // draw pile has changed, refresh our copy
            drawPileCopy.group = (ArrayList<AbstractCard>) drawPile.group.clone();

            // reset the previews based on the new draw pile
            Reset(AuguryCount());
        }

        previews.update();
    }

    public static int AuguryCount() {
        int amount = 0;
        if (AbstractDungeon.player.hasPower(AuguryPower.POWER_ID)) {
            amount = AbstractDungeon.player.getPower(AuguryPower.POWER_ID).amount;
        }
        return amount;
    }

    public static void Render(SpriteBatch sb) {
        previews.render(sb);
    }

    public static int GetRevealedAttacksCount() {
        return (int) previews.group.stream().filter(x -> x.type == AbstractCard.CardType.ATTACK).count();
    }

}
