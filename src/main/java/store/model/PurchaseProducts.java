package store.model;

import java.util.HashMap;

public class PurchaseProducts {
    private HashMap<String,Integer> purchaseProducts;

    public PurchaseProducts() {
        this.purchaseProducts = new HashMap<>();
    }

    public void addPurchaseProducts(String name, int quantity) {
        purchaseProducts.put(name,quantity);
    }
}
