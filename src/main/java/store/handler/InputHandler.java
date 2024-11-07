package store.handler;

import store.message.NoticeMessage;
import store.view.Input;

public class InputHandler {
    Input input;

    public InputHandler(Input input) {
        this.input = input;
    }

    public boolean askAboutBenefit(String productName) {
        return input.requestQuestion(NoticeMessage.BENEFIT_QUESTION.getBenefitMessage(productName));
    }

    public boolean askAboutShortage(String productName, int countNotPromotion) {
        return input.requestQuestion(NoticeMessage.SHORTAGE_QUESTION.getShortageMessage(productName, countNotPromotion));
    }
}
