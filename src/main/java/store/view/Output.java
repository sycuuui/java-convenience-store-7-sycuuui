package store.view;

import store.dto.response.ProductInfo;
import store.message.ErrorMessage;
import store.model.product.Products;

import java.util.Optional;
import java.util.Set;

public class Output {
    private final Products products;

    public Output(Products products) {
        this.products = products;
    }

    public static void printErrorMessage(ErrorMessage errorMessage) {
        System.out.println(errorMessage.getMessage());
    }

    /**
     * 현재 상품 목록 이름을 통해 일반 상품과 프로모션 상품 정보 가져오고
     * 정보가 있으면 printProductInfo 실행
     */
    public void printProducts() {
        Set<String> productNames = products.getAllProductsNames();

        productNames.forEach(productName -> {
            Optional<ProductInfo> promotionProductInfo = Optional.ofNullable(products.getInfoToPromotionProduct(productName));
            Optional<ProductInfo> generalProductInfo = Optional.ofNullable(products.getInfoToGeneralProduct(productName));

            //프로모션 상품에 대한 출력이 먼저
            promotionProductInfo.ifPresent(this::printProductInfo);
            generalProductInfo.ifPresent(this::printProductInfo);
        });
    }

    /**
     * 상품 정보 출력 메소드
     *
     * @param productInfo 상품 정보
     */
    private void printProductInfo(ProductInfo productInfo) {
        String stockText = "재고 없음";
        if (productInfo.stock() > 0) {
            stockText = productInfo.stock() + "개";
        }

        StringBuilder output = new StringBuilder();
        output.append("- ")
                .append(productInfo.name()).append(" ")
                .append(productInfo.price()).append("원 ")
                .append(stockText);

        if (productInfo.promotionName() != null) {
            output.append(" ").append(productInfo.promotionName());
        }

        System.out.println(output);
    }
}
