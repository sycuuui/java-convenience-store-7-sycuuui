package store.model;

public class PurchaseProductInfo {
    private final boolean hasPromotion;
    private final int quantity;

    public PurchaseProductInfo(boolean hasPromotion, int quantity) {
        this.hasPromotion = hasPromotion;
        this.quantity = quantity;
    }
}
