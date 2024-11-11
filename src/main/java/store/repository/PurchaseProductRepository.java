package store.repository;

import store.model.PurchaseProducts;

public class PurchaseProductRepository {
    private PurchaseProducts purchaseProducts;

    public PurchaseProductRepository(PurchaseProducts purchaseProducts) {
        this.purchaseProducts = purchaseProducts;
    }

    /**
     * 입력 받은 구매 상품들 데이터 저장 메소드
     * @param splitCommaInput 콤마 기준으로 분리된 문자열 배열
     */
    public void savePurchaseProducts(String[] splitCommaInput) {
        for (String box : splitCommaInput) {
            String replaceBox = box.replace("[", "");
            String replaceBox2 = replaceBox.replace("]", "");

            String[] productNameAndQuantity = replaceBox2.split("-");
            String name = productNameAndQuantity[0];
            int quantity = Integer.parseInt(productNameAndQuantity[1]);

            purchaseProducts.putInitialPurchaseDetails(name, quantity);
        }//end for loop
    }
}
