package store.view;

import store.dto.response.PriceResultRes;
import store.dto.response.ProductInfoRes;
import store.message.ErrorMessage;
import store.message.OutputMessage;
import store.model.product.Products;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public class Output {

    public static void printErrorMessage(ErrorMessage errorMessage) {
        System.out.println(errorMessage.getMessage());
    }

    public void printHello() {
        String helloText = OutputMessage.HELLO.getMessage();

        System.out.println(helloText);
    }

    /**
     * 현재 상품 목록 이름을 통해 일반 상품과 프로모션 상품 정보 가져오고
     * 정보가 있으면 printProductInfo 실행
     */
    public void printProducts(Products products) {
        Set<String> productNames = products.getAllProductsNames();

        productNames.forEach(productName -> {
            Optional<ProductInfoRes> promotionProductInfo = Optional.ofNullable(products.getInfoToPromotionProduct(productName));
            Optional<ProductInfoRes> generalProductInfo = Optional.ofNullable(products.getInfoToGeneralProduct(productName));

            //프로모션 상품에 대한 출력이 먼저
            promotionProductInfo.ifPresent(this::printProductInfo);
            generalProductInfo.ifPresent(this::printProductInfo);

            if(promotionProductInfo.isPresent() && generalProductInfo.isEmpty()) {
                ProductInfoRes productInfoRes = new ProductInfoRes(promotionProductInfo.get().name(),promotionProductInfo.get().price(),0,null);
                printProductInfo(productInfoRes);
            }
        });
    }

    /**
     * 상품 정보 출력 메소드
     *
     * @param productInfo 상품 정보
     */
    private void printProductInfo(ProductInfoRes productInfo) {
        String stockText = "재고 없음";
        if (productInfo.stock() > 0) {
            stockText = setNumberFormat(productInfo.stock()) + "개";
        }

        StringBuilder output = new StringBuilder();
        output.append("- ")
                .append(productInfo.name()).append(" ")
                .append(setNumberFormat(productInfo.price())).append("원 ")
                .append(stockText);

        if (productInfo.promotionName() != null) {
            output.append(" ").append(productInfo.promotionName());
        }

        System.out.println(output);
    }

    public void printPurchaseProduct(String productName, int quantity, int totalPrice) {
        System.out.printf("%-10s %-5s %-8s\n", productName, setNumberFormat(quantity), setNumberFormat(totalPrice));
    }

    public void printPresentProduct(String productName, int quantity) {
        System.out.printf("%-10s %-5s\n", productName, setNumberFormat(quantity));
    }

    public void printPriceResultSection(PriceResultRes priceResultRes) {
        System.out.printf("%-10s %-5s %-8s\n", "총구매액", setNumberFormat(priceResultRes.totalQuantity()), setNumberFormat(priceResultRes.totalPurchaseAmount()));
        System.out.printf("%-10s -%10s\n", "행사할인", setNumberFormat(priceResultRes.totalDiscountAmount()));
        System.out.printf("%-10s -%10s\n", "멤버십할인", setNumberFormat(priceResultRes.membershipDiscountAmount()));
        System.out.printf("%-10s %10s\n", "내실돈", setNumberFormat(priceResultRes.finalAmount()));
    }

    public void printOutPutMessage(OutputMessage outputMessage) {
        System.out.println(outputMessage.getMessage());
    }

    public void printCategory() {
        System.out.printf("%-10s %-5s %-8s\n", "상품명", "수량", "금액");
    }

    public String setNumberFormat(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.KOREA);

        return formatter.format(amount);
    }
}
