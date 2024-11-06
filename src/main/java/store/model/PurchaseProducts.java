package store.model;

import java.util.HashMap;

public class PurchaseProducts {
    private final HashMap<String, PurchaseProductInfo> purchaseProducts;

    public PurchaseProducts() {
        this.purchaseProducts = new HashMap<>();
    }

    public void addPurchaseProducts(String name, PurchaseProductInfo purchaseProductInfo) {
        purchaseProducts.put(name, purchaseProductInfo);
    }
}
