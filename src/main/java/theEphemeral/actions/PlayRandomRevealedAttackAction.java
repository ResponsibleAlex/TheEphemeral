package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.previewWidget.PreviewWidget;

public class PlayRandomRevealedAttackAction extends AbstractGameAction {
    private final AbstractPlayer player;

    public PlayRandomRevealedAttackAction() {
        actionType = ActionType.CARD_MANIPULATION;
        player = AbstractDungeon.player;
    }

    @Override
    public void update() {
        CardGroup candidates = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : PreviewWidget.GetRevealedCards()) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                candidates.addToRandomSpot(c);
            }
        }

        if (candidates.size() > 0) {
            AbstractCard c = candidates.getTopCard();

            AbstractDungeon.player.drawPile.group.remove(c);
            AbstractDungeon.getCurrRoom().souls.remove(c);
            addToBot(new NewQueueCardAction(c, true, false, true));
        }

        isDone = true;
    }
}
