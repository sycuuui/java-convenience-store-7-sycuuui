package store.service;

import store.handler.InputHandler;

public class MembershipService {
    private final int DISCOUNT_PERCENT = 30;
    private final int DISCOUNT_LIMIT = 8000;

    private InputHandler inputHandler;
    private boolean apply;


    public MembershipService(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public void ask() {
        this.apply = inputHandler.askAboutMembership();
    }

    public int processMembership(int total) {
        if (apply) {
            return calculateMembershipDiscount(total);
        }
        return 0;
    }

    private int calculateMembershipDiscount(int total) {
        int discountPrice = total % DISCOUNT_PERCENT;

        return checkLimit(discountPrice);
    }

    private int checkLimit(int discountPrice) {
        return Math.min(discountPrice, DISCOUNT_LIMIT);
    }
}
