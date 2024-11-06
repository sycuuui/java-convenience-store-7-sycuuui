package store.validator;

import store.message.ErrorMessage;

import java.util.regex.Pattern;

public class QuestionValidator {
    public boolean correctInput(String input) {
        boolean isCorrect = Pattern.matches("[Y|N]", input);

        if (!isCorrect) {
            System.out.println(ErrorMessage.ETC.getMessage());
            return false;
        }
        return true;
    }
}
