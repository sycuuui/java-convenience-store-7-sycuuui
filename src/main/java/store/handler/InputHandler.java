package store.handler;

import store.message.InputMessage;
import store.view.Input;

public class InputHandler {
    Input input;

    public InputHandler(Input input) {
        this.input = input;
    }

    public boolean askAboutAdd(String productName) {
        return input.requestQuestion(InputMessage.ADD_QUESTION.getAddMessage(productName));
    }

    public boolean askAboutShortage(String productName, int notApplyPromotionQuantity) {
        return input.requestQuestion(InputMessage.SHORTAGE_QUESTION.getShortageMessage(productName, notApplyPromotionQuantity));
    }

    public boolean askAboutMembership() {
        return input.requestQuestion(InputMessage.MEMBERSHIP_QUESTION.getMessage());
    }
}
