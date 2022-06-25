package theEphemeral.cards;

public abstract class AbstractEntropyCard extends AbstractDynamicCard {

    public AbstractEntropyCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }

    public void increaseEntropy() {
        if (baseDamage > 0) {
            baseDamage++;
        }
        if (baseBlock > 0) {
            baseBlock++;
        }
    }
}
