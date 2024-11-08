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


}