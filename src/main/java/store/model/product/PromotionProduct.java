package store.model.product;

import store.model.promotion.Promotion;

public class PromotionProduct {
    private final Product product;
    private final Promotion promotion;

    public PromotionProduct(Product product, Promotion promotion) {
        this.product = product;
        this.promotion = promotion;
    }

    public int getProductQuantity() {
        return product.getQuantity();
    }

    public boolean isPromotionPeriod() {
        return promotion.isActive();
    }
}
