package store.model;

import java.util.HashMap;
import java.util.Map;

public class PromotionProducts {
    private final HashMap<Product, Promotion> promotionProducts;

    public PromotionProducts() {
        this.promotionProducts = new HashMap<>();
    }

    public void addPromotionProduct(Product product, Promotion promotion) {
        promotionProducts.put(product, promotion);
    }

    public boolean isExist(String name) {
        return promotionProducts.keySet().stream()
                .anyMatch(product -> product.equalName(name));
    }

    public int getTotalPromotionProductQuantity(String name) {
        return promotionProducts.keySet().stream()
                .filter(product -> product.equalName(name))
                .mapToInt(Product::getQuantity)
                .sum();
    }

    public Promotion getPromotionProductByName(String name) {
        return (Promotion) promotionProducts.entrySet().stream()
                .filter(entry -> entry.getKey().equalName(name))
                .map(Map.Entry::getValue);
    }
}
