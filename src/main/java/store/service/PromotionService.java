package store.service;

import store.dto.response.QuantityRes;
import store.handler.InputHandler;
import store.model.product.Products;
import store.model.PurchaseProducts;

import java.util.Optional;

public class PromotionService {
    private final Products products;
    private final PurchaseProducts purchaseProducts;
    private final InputHandler inputHandler;

    public PromotionService(Products products, PurchaseProducts purchaseProducts, InputHandler inputHandler) {
        this.products = products;
        this.purchaseProducts = purchaseProducts;
        this.inputHandler = inputHandler;
    }

    /**
     * 구매할 상품들 각 프로모션 적용
     */
    public void applyPromotion() {
        purchaseProducts.getPurchaseDetails().forEach((productName, purchaseQuantity) -> {
            processPromotion(productName, purchaseQuantity)
                    .ifPresent(quantityRes -> purchaseProducts.putPurchasePromotionDetail(productName, quantityRes));
        });
    }

    /**
     * 구매 상품 프로모션에 대한 처리 메소드
     *
     * @param productName      상품 이름
     * @param purchaseQuantity 구매 수량
     */
    private Optional<QuantityRes> processPurchaseProduct(String productName, int purchaseQuantity) {
        if (!checkPromotion(productName)) {
            return Optional.empty();
        }

        return processPromotion(productName, purchaseQuantity);
    }

    private Optional<QuantityRes> processPromotion(String productName, int purchaseQuantity) {
        int presentQuantity = products.getPresentQuantityByPromotionProduct(productName, purchaseQuantity);
        int appliedPromotionQuantity = products.getAppliedPromotionQuantityByPromotionProduct(productName, presentQuantity);

        //프로모션 수량이 구매 수량보다 크거나 같을때
        if (!products.isOverPromotionProductQuantity(productName, purchaseQuantity)) {
            //프로모션 추가 수량이 있는지 판단
            presentQuantity = applyAdditionalPromotionQuantity(productName, purchaseQuantity, presentQuantity);
            appliedPromotionQuantity = products.getAppliedPromotionQuantityByPromotionProduct(productName, presentQuantity);
            return Optional.of(new QuantityRes(presentQuantity, appliedPromotionQuantity));
        }

        handleUnappliedPromotionQuantity(productName, purchaseQuantity, appliedPromotionQuantity);
        return Optional.of(new QuantityRes(presentQuantity, appliedPromotionQuantity));
    }

    private boolean checkPromotion(String productName) {
        //프로모션 해당 상품 확인
        if (!products.isExistProduct(productName)) {
            return false;
        }
        //프로모션 기간 확인
        if (!products.isPromotionPeriod(productName)) {
            return false;
        }
        return true;
    }

    private int applyAdditionalPromotionQuantity(String productName, int purchaseQuantity, int presentQuantity) {
        if (products.getIsNeedQuestionAboutAddByPromotionProduct(productName, purchaseQuantity)) {
            return presentQuantity;
        }
        //사용자에게 추가 수량에 대해 응답 요청
        if (!inputHandler.askAboutAdd(productName)) {
            return presentQuantity;
        }
        return (presentQuantity + 1);
    }

    /**
     * 프로모션 미적용 수량이 있을 경우 사용자에게 응답 요청 후 응답에 따라 적용 메소드
     *
     * @param productName              상품 이름
     * @param purchaseQuantity         구매 수량
     * @param appliedPromotionQuantity 프로모션 적용된 상품 수량
     */
    private void handleUnappliedPromotionQuantity(String productName, int purchaseQuantity, int appliedPromotionQuantity) {
        int notApplyPromotionQuantity = purchaseQuantity - appliedPromotionQuantity;

        if (notApplyPromotionQuantity > 0) {
            if (inputHandler.askAboutShortage(productName, notApplyPromotionQuantity)) {
                purchaseProducts.minusNotApplyPromotionQuantity(productName, notApplyPromotionQuantity);
            }
        }//end if
    }
}
