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

    public int processMembership(int totalPurchaseAmount, int totalPromotionAmount) {
        if (apply) {
            return calculateMembershipDiscount(totalPurchaseAmount, totalPromotionAmount);
        }
        return 0;
    }

    private int calculateMembershipDiscount(int totalPurchaseAmount, int totalPromotionAmount) {
        int total = totalPurchaseAmount - totalPromotionAmount;
        int discountPrice = (int) (total * DISCOUNT_PERCENT);

        return checkLimit(discountPrice);
    }

    private int checkLimit(int discountPrice) {
        return Math.min(discountPrice, DISCOUNT_LIMIT);
    }
}
