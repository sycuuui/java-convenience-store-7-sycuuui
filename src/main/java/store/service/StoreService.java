package store.service;

import store.model.Products;
import store.model.Promotions;
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
        for(String box: splitCommaInput) {
            String replaceBox = box.replace("[", "");
            String replaceBox2 = replaceBox.replace("]", "");

            String[] productNameAndQuantity = replaceBox2.split("-");
            purchaseProducts.addPurchaseProducts(productNameAndQuantity[0],Integer.parseInt(productNameAndQuantity[1]));
        }//end for loop
    }
}
