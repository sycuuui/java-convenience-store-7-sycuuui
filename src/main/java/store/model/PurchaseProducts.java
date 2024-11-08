package store.model;

import java.util.HashMap;


public class PurchaseProducts {
    private final HashMap<String, Integer> purchaseDetails;

    public PurchaseProducts() {
        this.purchaseDetails = new HashMap<>();
    }

    public void addPurchaseProducts(String name, Integer purchaseQuantity) {
        purchaseDetails.put(name, purchaseQuantity);
    }

    public void minusNotApplyPromotionQuantity(String name, int notApplyPromotionQuantity) {
        int quantity = purchaseDetails.get(name);
        purchaseDetails.put(name, (quantity - notApplyPromotionQuantity));
    }

    public HashMap<String, Integer> getPurchaseDetails() {
        return purchaseDetails;
    }
}
