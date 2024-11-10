package store.model.product;

import store.dto.response.ProductInfoRes;
import store.model.promotion.Promotion;

public class PromotionProduct {
    private final Product product;
    private final Promotion promotion;

    public PromotionProduct(Product product, Promotion promotion) {
        this.product = product;
        this.promotion = promotion;
    }

    public int getProductQuantity() {
        return product.getQuantity();
    }

    public ProductInfoRes getPromotionProductInfo() {
        return product.getProductInfo(promotion.getName());
    }

    public boolean isPromotionPeriod() {
        return promotion.isActive();
    }

    /**
     * @param purchaseQuantity 구매할 수량
     * @return 증정 수량
     */
    public int calculatePresentQuantity(int purchaseQuantity) {
        return (purchaseQuantity / promotion.calculatorLimit());
    }

    /**
     * @param presentQuantity 증정 수량
     * @return 프로모션 적용 수량
     */
    public int calculateAppliedPromotionQuantity(int presentQuantity) {
        return presentQuantity * promotion.calculatorLimit();
    }

    /**
     * 프로모션 추가 적용 질문이 필요한지 판단 메소드
     *
     * @param purchaseQuantity
     * @return 남은 수량이 limit-1과 같을 경우 true
     */
    public boolean isNeedQuestionAboutAdd(int purchaseQuantity) {
        int limit = promotion.calculatorLimit();
        int remainQuantity = purchaseQuantity % limit;
        return (remainQuantity == (limit - 1));
    }

    public void appliedSoldQuantity(int soldQuantity) {
        product.appliedSoldQuantity(soldQuantity);
    }

    public int getPayment(int purchaseQuantity) {
        return product.payment(purchaseQuantity);
    }
}
