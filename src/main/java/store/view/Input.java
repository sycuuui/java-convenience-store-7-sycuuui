package store.view;

import store.message.NoticeMessage;
import camp.nextstep.edu.missionutils.Console;
import store.model.Products;
import store.validator.PurchaseValidator;
import store.validator.QuestionValidator;

public class Input {
    private PurchaseValidator purchaseValidator;
    private QuestionValidator questionValidator;
    private Products products;

    public Input(Products products) {
        purchaseValidator = new PurchaseValidator();
        questionValidator = new QuestionValidator();
        this.products = products;
    }

    public void requestProducts() {
        System.out.println(NoticeMessage.INPUT_PRODUCT.getMessage());

        String input = Console.readLine();
        String[] splitInput = input.split(",", -1);
        while (purchaseValidator.requestProducts(splitInput, products)) {
            input = Console.readLine();
            splitInput = input.split(",", -1);
        }
    }

    public boolean requestQuestion(String question) {
        System.out.println(question);

        String input = Console.readLine();
        while (questionValidator.correctInput(input)) {
            input = Console.readLine();
        }

        if (input.equals("Y")) {
            return true;
        }
        return false;
    }
}
