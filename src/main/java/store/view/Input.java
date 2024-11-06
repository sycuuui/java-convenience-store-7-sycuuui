package store.view;

import store.message.NoticeMessage;
import camp.nextstep.edu.missionutils.Console;
import store.model.Products;
import store.validator.PurchaseValidator;

public class Input {
    private PurchaseValidator purchaseValidator;
    private Products products;

    public Input(Products products) {
        purchaseValidator = new PurchaseValidator();
        this.products = products;
    }

    public void requestProducts() {
        System.out.println(NoticeMessage.INPUT_PRODUCT.getMessage());

        String input = Console.readLine();
        String[] splitInput = input.split(",",-1);
        while(purchaseValidator.requestProducts(splitInput,products)) {
            input = Console.readLine();
            splitInput = input.split(",", -1);
        }

    }
}
