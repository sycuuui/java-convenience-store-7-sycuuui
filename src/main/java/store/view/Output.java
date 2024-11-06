package store.view;

import store.message.ErrorMessage;

public class Output {
    public static void printErrorMessage(ErrorMessage errorMessage) {
        System.out.println(errorMessage.getMessage());
    }
}
