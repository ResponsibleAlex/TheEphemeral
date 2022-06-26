package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;

public class DiscardTopCardAction extends AbstractGameAction {
    private final AbstractPlayer player;
    private static final float PADDING;

    public DiscardTopCardAction() {
        actionType = ActionType.CARD_MANIPULATION;
        player = AbstractDungeon.player;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            if (player.drawPile.size() > 0) {
                AbstractCard c = player.drawPile.getTopCard();
                player.drawPile.removeTopCard();
                c.triggerOnManualDiscard();
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c, (float)Settings.WIDTH / 2.0F + PADDING + AbstractCard.IMG_WIDTH, (float)Settings.HEIGHT / 2.0F));
            }
        }
        tickDuration();
    }

    static {
        PADDING = 30.0F * Settings.scale;
    }
}
