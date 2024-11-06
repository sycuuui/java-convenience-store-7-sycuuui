package store.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Promotions {
    private final HashMap<String, Promotion> promotions;

    public Promotions() {
        this.promotions = new HashMap<>();
    }

    public void addPromotion(final String name, final Promotion promotion) {
        promotions.put(name, promotion);
    }

    public boolean hasPromotion(String name) {
        return promotions.get(name) != null;
    }
}
