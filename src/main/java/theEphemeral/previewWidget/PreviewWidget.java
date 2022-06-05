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
    private static final CardGroup previews = new CardGroup(CardGroupType.UNSPECIFIED);
    private static final CardGroup drawPileCopy = new CardGroup(CardGroupType.UNSPECIFIED);
    private static final float drawScale = 0.35f;

    public static void Reset() {
        Reset(0);
    }
    public static void Reset(int amount) {
        CardGroup drawPile = AbstractDungeon.player.drawPile;

        // only show the amount that actually exist
        amount = Math.min(amount, drawPile.size());

        // clear old previews
        previews.clear();

        // add copies to the CardGroup and initialize visuals
        if (amount > 0) {
            for (int i = amount - 1; i >= 0; i--) {
                AbstractCard c = drawPile.group.get(i);
                AbstractCard cpy = c.makeStatEquivalentCopy();

                cpy.setAngle(0.0F, true);
                cpy.current_x = cpy.target_x = Settings.scale * AbstractCard.IMG_WIDTH;
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
        if (drawPile.size() != drawPileCopy.size() && !drawPile.group.equals(drawPileCopy.group)) {
            // draw pile has changed, refresh our copy
            drawPileCopy.group = (ArrayList<AbstractCard>) drawPile.group.clone();

            // reset the previews based on the new draw pile
            int amount = 0;
            if (AbstractDungeon.player.hasPower(AuguryPower.POWER_ID)) {
                amount = AbstractDungeon.player.getPower(AuguryPower.POWER_ID).amount;
            }
            Reset(amount);
        }

        previews.update();
    }

    public static void Render(SpriteBatch sb) {
        previews.render(sb);
    }

}
