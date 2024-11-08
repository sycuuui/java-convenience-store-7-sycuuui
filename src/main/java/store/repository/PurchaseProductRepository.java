package store.repository;

import store.model.PurchaseProducts;

public class PurchaseProductRepository {
    private PurchaseProducts purchaseProducts;

    public PurchaseProductRepository(PurchaseProducts purchaseProducts) {
        this.purchaseProducts = purchaseProducts;
    }

    public void savePurchaseProducts(String[] splitCommaInput) {
        for (String box : splitCommaInput) {
            String replaceBox = box.replace("[", "");
            String replaceBox2 = replaceBox.replace("]", "");

            String[] productNameAndQuantity = replaceBox2.split("-");
            String name = productNameAndQuantity[0];
            int quantity = Integer.parseInt(productNameAndQuantity[1]);

            purchaseProducts.putPurchaseDetail(name, quantity);
        }//end for loop
    }


}
