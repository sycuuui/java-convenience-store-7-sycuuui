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
        purchaseProducts.getIntitalPurchaseDetails().forEach((productName, purchaseQuantity) -> {
            processPurchaseProduct(productName, purchaseQuantity)
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

    /**
     * 프로모션 수량과 구매 수량 비교하여 각 행동 실행
     * @param productName
     * @param purchaseQuantity
     * @return
     */
    public Optional<QuantityRes> processPromotion(String productName, int purchaseQuantity) {
        //프로모션 수량이 구매 수량보다 크거나 같을때
        if (!products.isOverPromotionProductQuantity(productName, purchaseQuantity)) {
            return processWhenOverQuantity(productName,purchaseQuantity);
        }
        return processWhenNotOverQunatity(productName,purchaseQuantity);
    }

    /**
     * 프로모션 수량이 구매 수량보다 크거나 같을 경우
     * @param productName
     * @return
     */
    public Optional<QuantityRes> processWhenOverQuantity(String productName, int purchaseQuantity) {
        int presentQuantity = products.getPresentQuantityByPurchase(productName,purchaseQuantity) + applyAdditionalPromotionQuantity(productName, purchaseQuantity);
        int appliedPromotionQuantity = products.getAppliedPromotionQuantityByPromotionProduct(productName, presentQuantity);
        return Optional.of(new QuantityRes(presentQuantity, appliedPromotionQuantity));
    }

    public Optional<QuantityRes> processWhenNotOverQunatity(String productName, int purchaseQuantity) {
        int presentQuantity = products.getPresentQuantityByProduct(productName);
        int appliedPromotionQuantity = products.getAppliedPromotionQuantityByPromotionProduct(productName, presentQuantity);

        handleUnappliedPromotionQuantity(productName, purchaseQuantity, appliedPromotionQuantity);
        return Optional.of(new QuantityRes(presentQuantity, products.getPromotionProductQuantity(productName)));
    }

    public boolean checkPromotion(String productName) {
        //프로모션 해당 상품 확인
        if (!products.isExistPromotionProduct(productName)) {
            return false;
        }
        //프로모션 기간 확인
        if (!products.isPromotionPeriod(productName)) {
            return false;
        }
        return true;
    }

    /**
     * 프로모션 추가 수량 적용 판단과 응답 요청 메소드
     *
     * @param productName      상품 이름
     * @param purchaseQuantity 구매 수량
     * @return 할인 수량
     */
    public int applyAdditionalPromotionQuantity(String productName, int purchaseQuantity) {
        if (!products.getIsNeedQuestionAboutAddByPromotionProduct(productName, purchaseQuantity)) {
            return 0;
        }
        if (!inputHandler.askAboutAdd(productName)) {
            return 0;
        }
        purchaseProducts.addPurchaseQunatity(productName);
        return 1;
    }

    /**
     * 프로모션 미적용 수량이 있을 경우 사용자에게 응답 요청 후 응답에 따라 적용 메소드
     *
     * @param productName              상품 이름
     * @param purchaseQuantity         구매 수량
     * @param appliedPromotionQuantity 프로모션 적용된 상품 수량
     */
    public void handleUnappliedPromotionQuantity(String productName, int purchaseQuantity, int appliedPromotionQuantity) {
        int UnappliedPromotionQuantity = purchaseQuantity - appliedPromotionQuantity;

        if (UnappliedPromotionQuantity > 0) {
            if (!inputHandler.askAboutShortage(productName, UnappliedPromotionQuantity)) {
                purchaseProducts.minusUnappliedPromotionQuantity(productName, UnappliedPromotionQuantity);
            }
        }//end if
    }
}
