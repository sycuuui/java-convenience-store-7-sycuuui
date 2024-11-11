package store.view;

import store.message.InputMessage;
import camp.nextstep.edu.missionutils.Console;
import store.model.product.Products;
import store.validator.PurchaseValidator;
import store.validator.QuestionValidator;

public class Input {
    private PurchaseValidator purchaseValidator;
    private QuestionValidator questionValidator;

    public Input() {
        purchaseValidator = new PurchaseValidator();
        questionValidator = new QuestionValidator();
    }

    public String[] requestProducts(Products products) {
        System.out.println(InputMessage.INPUT_PRODUCT.getMessage());

        String input = Console.readLine();
        String[] splitCommaInput = input.split(",", -1);
        while (!purchaseValidator.requestProducts(splitCommaInput, products)) {
            input = Console.readLine();
            splitCommaInput = input.split(",", -1);
        }

        return splitCommaInput;
    }

    public boolean requestQuestion(String question) {
        System.out.println(question);

        String input = Console.readLine();
            while (!questionValidator.correctInput(input)) {
                input = Console.readLine();
        }

        if (input.equals("Y")) {
            return true;
        }
        return false;
    }

}
