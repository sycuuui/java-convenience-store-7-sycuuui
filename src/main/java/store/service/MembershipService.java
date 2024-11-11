package store.service;

import store.handler.InputHandler;

public class MembershipService {
    private final float DISCOUNT_PERCENT = 0.3F;
    private final int DISCOUNT_LIMIT = 8000;

    private InputHandler inputHandler;
    private boolean apply;


    public MembershipService(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public void ask() {
        this.apply = inputHandler.askAboutMembership();
    }

    /**
     * 사용자가 멤버십 적용 의사가 있는지 판단 메소드
     */
    public int processMembership(int totalPurchaseAmount, int totalPromotionAmount) {
        if (apply) {
            return calculateMembershipDiscount(totalPurchaseAmount, totalPromotionAmount);
        }
        return 0;
    }

    /**
     * 멤버십 적용 금액 계산 메소드
     * @param totalPurchaseAmount 총 구매 가격
     * @param totalPromotionAmount 총 프로모션 적용된 상품들의 가격
     */
    private int calculateMembershipDiscount(int totalPurchaseAmount, int totalPromotionAmount) {
        int total = totalPurchaseAmount - totalPromotionAmount;
        int discountPrice = (int) (total * DISCOUNT_PERCENT);

        return checkLimit(discountPrice);
    }

    /**
     * 멤버십 할인 가격 제한 조건에 따른 행동 메소드
     */
    private int checkLimit(int discountPrice) {
        return Math.min(discountPrice, DISCOUNT_LIMIT);
    }
}
