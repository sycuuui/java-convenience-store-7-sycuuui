package store.service;

import store.handler.InputHandler;
import store.model.product.Products;
import store.model.promotion.Promotion;
import store.model.PurchaseProducts;
import store.view.Input;

public class PurchaseService {
    private final Products products;
    private final PurchaseProducts purchaseProducts;
    private final InputHandler inputHandler;

    public PurchaseService(Products products, PurchaseProducts purchaseProducts, Input input) {
        this.products = products;
        this.purchaseProducts = purchaseProducts;
        this.inputHandler = new InputHandler(input);
    }

    /**
     * 구매할 상품들 각 프로모션 적용
     */
    public void applyPromotion() {
        purchaseProducts.getPurchaseDetails().forEach(this::processPurchaseProduct);
    }

    /**
     * @param productName
     * @param purchaseQuantity
     */
    private void processPurchaseProduct(String productName, int purchaseQuantity) {
        //프로모션 해당 상품 확인
        if (products.isExistProduct(productName)) {
            //프로모션 기간 확인
            if (products.isPromotionPeriod(productName)) {
                //프로모션 수량과 구매 수량 비교

            }
        }
    }

    private void processPromotion(String productName, int purchaseQuantity, Promotion promotion) {

    }
}
