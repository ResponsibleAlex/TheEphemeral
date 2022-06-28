package theEphemeral.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.UUID;

public abstract class AbstractVanishingCard extends AbstractDynamicCard {
    public AbstractVanishingCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target, int vanishing) {
        super(id, img, cost, type, color, rarity, target);

        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = misc = vanishing;
        tags.add(CardTags.HEALING);
        exhaust = true;
    }

    @Override
    public void applyPowers() {
        defaultSecondMagicNumber = misc;
        super.applyPowers();
        initializeDescription();
    }

    protected void vanish() {
        misc--;
        applyPowers();

        AbstractCard c = getMasterDeckCard(uuid);
        if (c == null)
            return;

        c.misc = misc;
        c.applyPowers();

        if (c.misc <= 0) {
            AbstractDungeon.player.masterDeck.removeCard(c);
        }
    }

    protected void increaseVanishCount() {
        misc++;
        applyPowers();

        AbstractCard c = getMasterDeckCard(uuid);
        if (c == null)
            return;

        c.misc = misc;
        c.applyPowers();
    }

    private AbstractCard getMasterDeckCard(UUID uuid) {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.uuid.equals(uuid)) {
                return c;
            }
        }
        return null;
    }
}