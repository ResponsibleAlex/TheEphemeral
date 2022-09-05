package theEphemeral.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theEphemeral.EphemeralMod;

import java.util.UUID;

public abstract class AbstractVanishingCard extends AbstractDynamicCard {
    public AbstractVanishingCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target, int vanishing) {
        super(id, img, cost, type, color, rarity, target);

        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = misc = vanishing;
        exhaust = true;
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

    @Override
    public AbstractCard makeSameInstanceOf() {
        AbstractCard copy = this.makeStatEquivalentCopy();

        // When creating initial draw pile, set uuids.
        // For all other cases, we want copies of Vanishing cards to not be tied to master deck card.
        if (EphemeralMod.preBattle)
            copy.uuid = this.uuid;

        return copy;
    }

    protected void vanish() {
        misc--;
        applyPowers();

        AbstractCard c = getMasterDeckCard(uuid);
        if (c == null)
            return;

        c.misc--;
        c.applyPowers();

        if (c.misc <= 0) {
            AbstractDungeon.player.masterDeck.removeCard(c);
        }
    }

    protected void addVanish() {
        misc += 1;
        initializeDescription();
    }

    private AbstractCard getMasterDeckCard(UUID uuid) {
        if (AbstractDungeon.player == null)
            return null;

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.uuid.equals(uuid)) {
                return c;
            }
        }
        return null;
    }
}
