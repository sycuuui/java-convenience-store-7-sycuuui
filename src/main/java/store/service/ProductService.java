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

    private void processProduct(String productName, int purchaseQuantity) {
        int appliedPromotionQuantity = 0;

        if (purchaseProducts.hasPurchasePromotion(productName)) {
            appliedPromotionQuantity = purchaseProducts.getAppliedPromotionQuantity(productName);
            products.appliedPurchaseQuantityToPromotionProduct(productName, appliedPromotionQuantity);
        }

        products.appliedPurchaseQuantityToProduct(productName, purchaseQuantity - appliedPromotionQuantity);
    }
}
