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

    public void putPurchaseDetail(String productName, Integer purchaseQuantity) {
        purchaseDetails.put(productName, purchaseQuantity);
    }

    public void putPurchasePromotionDetail(String productName, QuantityRes quantityRes) {
        purchasePromotionDetails.put(productName, quantityRes);
    }

    public void minusUnappliedPromotionQuantity(String productName, int UnappliedPromotionQuantity) {
        int quantity = purchaseDetails.get(productName);
        purchaseDetails.put(productName, (quantity - UnappliedPromotionQuantity));
    }

    public boolean hasPurchasePromotion(String productName) {
        return purchasePromotionDetails.containsKey(productName);
    }

    public int getAppliedPromotionQuantity(String productName) {
        return purchasePromotionDetails.get(productName).appliedPromotionQuantity();
    }

    public HashMap<String, Integer> getPurchaseDetails() {
        return purchaseDetails;
    }
}
