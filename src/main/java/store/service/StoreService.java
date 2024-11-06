package store.service;

import store.model.Products;
import store.model.Promotions;
import store.model.PurchaseProductInfo;
import store.model.PurchaseProducts;

public class StoreService {
    private Products products;
    private Promotions promotions;
    private PurchaseProducts purchaseProducts;

    public StoreService(Products products, Promotions promotions) {
        this.products = products;
        this.promotions = promotions;
        this.purchaseProducts = new PurchaseProducts();
    }

    public void savePurchaseProducts(String[] splitCommaInput) {
        for (String box : splitCommaInput) {
            String replaceBox = box.replace("[", "");
            String replaceBox2 = replaceBox.replace("]", "");

            String[] productNameAndQuantity = replaceBox2.split("-");
            String name = productNameAndQuantity[0];
            int quantity = Integer.parseInt(productNameAndQuantity[1]);

            purchaseProducts.addPurchaseProducts(name, new PurchaseProductInfo(promotions.hasPromotion(name), quantity));
        }//end for loop
    }
}
