package store.service;

import store.dto.response.QuantityRes;
import store.model.PurchaseProducts;
import store.model.product.Products;

public class ProductService {
    private final Products products;
    private final PurchaseProducts purchaseProducts;

    public ProductService(Products products, PurchaseProducts purchaseProducts) {
        this.products = products;
        this.purchaseProducts = purchaseProducts;
    }

    public void updateProductsStock() {
        purchaseProducts.getIntitalPurchaseDetails().forEach(this::processProduct);
    }

    /**
     * 구매한 상품의 종류에 따라 각각의 수량 재고 업데이트 메소드
     * @param productName 구매한 상품 이름
     * @param purchaseQuantity 구매한 상품 수량 (프로모션+일반)
     */
    private void processProduct(String productName, int purchaseQuantity) {
        int appliedPromotionQuantity = 0;

        if (purchaseProducts.hasPurchasePromotion(productName)) {
            appliedPromotionQuantity = purchaseProducts.getAppliedPromotionQuantity(productName);
            products.appliedPurchaseQuantityToPromotionProduct(productName, appliedPromotionQuantity);
        }

        products.appliedPurchaseQuantityToGeneralProduct(productName, purchaseQuantity - appliedPromotionQuantity);
    }
}
