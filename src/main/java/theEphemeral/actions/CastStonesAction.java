package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.cards.AbstractDynamicCard;

public class CastStonesAction extends AbstractGameAction {
    private final AbstractPlayer p;
    public CastStonesAction() {
        p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (!p.drawPile.isEmpty() && p.drawPile.getTopCard().type == AbstractCard.CardType.ATTACK) {
            addToBot(new PlayTopCardAction(AbstractDynamicCard.GetRandomMonster(), false));
        }

        this.isDone = true;
    }
}
