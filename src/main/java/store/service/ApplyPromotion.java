package store.service;

import store.enumerate.PromotionStatus;
import store.model.Products;
import store.model.Promotion;
import store.model.Promotions;
import store.model.PurchaseProducts;

public class ApplyPromotion {
    private final Products products;
    private final PurchaseProducts purchaseProducts;
    private final Promotions promotions;

    public ApplyPromotion(Products products, PurchaseProducts purchaseProducts, Promotions promotions) {
        this.products = products;
        this.purchaseProducts = purchaseProducts;
        this.promotions = promotions;
    }

    //제고 파악
    //살 물품들을 하나씩 파악
    public void checkPurchaseProducts() {
        purchaseProducts.getPurchaseDetails().forEach((name, purchaseQuantity) -> {
            //프로모션 상품일 경우
            if(promotions.hasPromotion(name)) {
                //제고 파악
                Promotion promotion = products.getPromotionProductByName(name);
                //프로모션 적용 기간인지 확인
                if(!promotion.isNowContainDuration()){
                    return;
                }

                PromotionStatus promotionStatus = promotion.getPromotionStatus();
                if(promotionStatus.equals(PromotionStatus.ONE_PLUS_ONE)) {

                }
                if(promotionStatus.equals(PromotionStatus.TWO_PLUS_ONE)) {

                }
            }
            //프로모션 상품이 아닐 경우..
        });
    }
}
