package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.previewWidget.PreviewWidget;

import java.util.ArrayList;

public class PeerThroughMistsAction extends AbstractGameAction {

    private final int block;
    private final AbstractPlayer p;

    public PeerThroughMistsAction(int block) {
        this.block = block;
        this.p = AbstractDungeon.player;

        if (Settings.FAST_MODE) {
            this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        } else {
            this.duration = this.startDuration = Settings.ACTION_DUR_MED;
        }
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> handCardsToRemove = new ArrayList<>();
        p.hand.group.forEach(x -> {
            if (x.type == AbstractCard.CardType.CURSE || x.type == AbstractCard.CardType.STATUS) {
                handCardsToRemove.add(x);
            }
        });
        handCardsToRemove.forEach(x -> {
            p.hand.moveToExhaustPile(x);
            addToBot(new GainBlockAction(p, p, block));
        });

        ArrayList<AbstractCard> drawPileCardsToRemove = new ArrayList<>();
        PreviewWidget.GetRevealedCards().forEach(x -> {
            if (x.type == AbstractCard.CardType.CURSE || x.type == AbstractCard.CardType.STATUS) {
                drawPileCardsToRemove.add(x);
            }
        });
        drawPileCardsToRemove.forEach(x -> {
            p.drawPile.moveToExhaustPile(x);
            addToBot(new GainBlockAction(p, p, block));
        });

        this.tickDuration();
    }
}
