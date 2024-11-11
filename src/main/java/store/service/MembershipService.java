package store.service;

public class MembershipService {
    private final float DISCOUNT_PERCENT = 0.3F;
    private final int DISCOUNT_LIMIT = 8000;
    private boolean apply;


    public void setApply(boolean isAppliedMembership) {
        this.apply = isAppliedMembership;
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
    public int calculateMembershipDiscount(int totalPurchaseAmount, int totalPromotionAmount) {
        int total = totalPurchaseAmount - totalPromotionAmount;
        int discountPrice = (int) (total * DISCOUNT_PERCENT);

        return checkLimit(discountPrice);
    }

    /**
     * 멤버십 할인 한도 조건에 따른 행동 메소드
     */
    public int checkLimit(int discountPrice) {
        return Math.min(discountPrice, DISCOUNT_LIMIT);
    }
}
