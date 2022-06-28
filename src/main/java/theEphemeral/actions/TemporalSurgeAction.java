package theEphemeral.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.previewWidget.PreviewWidget;

public class TemporalSurgeAction extends AbstractGameAction {
    private final AbstractPlayer player;

    public TemporalSurgeAction() {
        actionType = ActionType.CARD_MANIPULATION;
        player = AbstractDungeon.player;
    }

    @Override
    public void update() {
        int revealedIndex = PreviewWidget.revealed - 1;
        CardGroup candidates = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : player.drawPile.group.subList(0, revealedIndex)) {
            if (c.type == AbstractCard.CardType.ATTACK || c.type == AbstractCard.CardType.SKILL) {
                candidates.addToBottom(c);
            }
        }

        for (AbstractCard c : candidates.group) {
            AbstractDungeon.player.drawPile.group.remove(c);
            AbstractDungeon.getCurrRoom().souls.remove(c);
            addToBot(new NewQueueCardAction(c, true, false, true));
        }

        isDone = true;
    }
}