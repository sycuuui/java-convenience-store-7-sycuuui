package store.validator;

import store.message.ErrorMessage;
import store.model.product.Products;

import java.util.regex.Pattern;

public class PurchaseValidator {
    public boolean requestProducts(String[] splitInput, Products products) {
        for (String str : splitInput) {
            //","구분자로 분리된 문자열에 대한 검증 (빈 값, 표현식 검증)
            if(!splitCommaValidator(str)) {
                return false;
            }

            String replaceStr = str.replace("[", "");
            String replaceStr2 = replaceStr.replace("]", "");

            //"-"구분자로 분리된 문자열에 대한 검증 (존재하는 상품, 수량 검증)
            if(!splitDashValidator(replaceStr2, products)) {
                return false;
            }
        }
        return true;
    }

    public boolean splitCommaValidator(String str) {
        if (!isNotEmpty(str)) {
            return false;
        } //빈 값이 아닐 경우 true
        if (!isMatchPattern(str)) {
            return false;
        } //표현식 [문자열-숫자]으로 되어있을 경우 ture
        return true;
    }

    public boolean splitDashValidator(String str, Products products) {
        String[] productNameAndQuantity = str.split("-");
        if (!isExistProduct(productNameAndQuantity[0], products)) {
            return false;
        } //구매하려는 상품이 있을 경우 true
        if (!isNotOverQuantity(productNameAndQuantity, products)) {
            return false;
        } //구매 양이 재고보다 넘지 않는 경우 true

        return true;
    }

    public boolean isNotEmpty(String str) {
        if (str.isEmpty()) {
            System.out.println(ErrorMessage.INPUT.getMessage());
            return false;
        }
        return true;
    }

    public boolean isMatchPattern(String input) {
        boolean isMatch = Pattern.matches("\\[[가-힣a-zA-Z]+-\\d+\\]", input);

        if (!isMatch) {
            System.out.println(ErrorMessage.INPUT.getMessage());
            return false;
        }
        return true;
    }

    public boolean isExistProduct(String name, Products products) {
        boolean findExistProduct = products.isExistProducts(name);
        if (!findExistProduct) {
            System.out.println(ErrorMessage.NO_EXISTS.getMessage());
            return false;
        }
        return true;
    }

    public boolean isNotOverQuantity(String[] productNameAndQuantity, Products products) {
        boolean isNotOverProductsQuantity = products.isNotOverProductsQuantity(productNameAndQuantity[0], Integer.parseInt(productNameAndQuantity[1]));

        if (!isNotOverProductsQuantity) {
            System.out.println(ErrorMessage.OVER_QUANTITY.getMessage());
            return false;
        }
        return true;
    }


}
