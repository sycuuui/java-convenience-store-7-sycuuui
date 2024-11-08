package store.model.promotion;

import java.util.HashMap;

public class Promotions {
    private final HashMap<String, Promotion> promotions;

    public Promotions() {
        this.promotions = new HashMap<>();
    }

    public void putPromotion(final String name, final Promotion promotion) {
        promotions.put(name, promotion);
    }

    public Promotion findPromotion(String name) {
        return promotions.get(name);
    }
}
