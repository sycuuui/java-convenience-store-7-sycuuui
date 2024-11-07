package store.service;

import store.model.Products;
import store.model.Promotions;
import store.model.PurchaseProducts;
import store.view.Input;

public class StoreService {
    private PurchaseProducts purchaseProducts;

    public StoreService(PurchaseProducts purchaseProducts) {
        this.purchaseProducts = purchaseProducts;
    }

    public void savePurchaseProducts(String[] splitCommaInput) {
        for (String box : splitCommaInput) {
            String replaceBox = box.replace("[", "");
            String replaceBox2 = replaceBox.replace("]", "");

            String[] productNameAndQuantity = replaceBox2.split("-");
            String name = productNameAndQuantity[0];
            int quantity = Integer.parseInt(productNameAndQuantity[1]);

            purchaseProducts.addPurchaseProducts(name, quantity);
        }//end for loop
    }


}
