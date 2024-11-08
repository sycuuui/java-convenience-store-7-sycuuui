package store.model;

import store.dto.response.QuantityRes;

import java.util.HashMap;


public class PurchaseProducts {
    private final HashMap<String, Integer> purchaseDetails;
    private final HashMap<String, QuantityRes> purchasePromotionDetails;

    public PurchaseProducts() {
        this.purchaseDetails = new HashMap<>();
        this.purchasePromotionDetails = new HashMap<>();
    }

    public void putPurchaseDetail(String name, Integer purchaseQuantity) {
        purchaseDetails.put(name, purchaseQuantity);
    }

    public void putPurchasePromotionDetail(String name, QuantityRes quantityRes) {
        purchasePromotionDetails.put(name, quantityRes);
    }

    public void minusNotApplyPromotionQuantity(String name, int notApplyPromotionQuantity) {
        int quantity = purchaseDetails.get(name);
        purchaseDetails.put(name, (quantity - notApplyPromotionQuantity));
    }

    public HashMap<String, Integer> getPurchaseDetails() {
        return purchaseDetails;
    }
}
