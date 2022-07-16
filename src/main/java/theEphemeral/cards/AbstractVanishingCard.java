package theEphemeral.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.UUID;

public abstract class AbstractVanishingCard extends AbstractDynamicCard {
    public AbstractVanishingCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target, int vanishing) {
        super(id, img, cost, type, color, rarity, target);

        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = misc = vanishing;
        purgeOnUse = true;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        initializeDescription();
    }

    @Override
    public void initializeDescription() {
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = misc;
        super.initializeDescription();
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

    private AbstractCard getMasterDeckCard(UUID uuid) {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.uuid.equals(uuid)) {
                return c;
            }
        }
        return null;
    }
}
