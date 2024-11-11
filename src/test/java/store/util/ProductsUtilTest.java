package store.util;

import store.dto.request.ProductReq;
import store.dto.request.PromotionReq;
import store.model.product.Product;
import store.model.product.Products;
import store.model.promotion.Promotion;

import java.time.LocalDate;

public class ProductsUtilTest {
    static final String PRODUCT_NAME1 = "콜라"; //일반 상품 - price:1000, quantity:10 , 프로모션 상품 - price:1000, quantity:10
    static final String PRODUCT_NAME2 = "오렌지주스"; //프로모션 상품 - price:1800, quantity:9

    public static Products getProducts() {
        Products products = new Products();
        Product generalProduct = new Product(new ProductReq(PRODUCT_NAME1,1000,10));
        Product promotionProduct = new Product(new ProductReq(PRODUCT_NAME1,1000,10));
        Product promotionProduct2 = new Product(new ProductReq(PRODUCT_NAME2,1800,9));
        Promotion promotion = new Promotion(new PromotionReq("탄산2+1",2,1, LocalDate.of(2024,1,1),LocalDate.of(2024,12,31)));
        Promotion promotion2 = new Promotion(new PromotionReq("MD추천상품",1,1, LocalDate.of(2024,1,1),LocalDate.of(2024,12,31)));

        products.putProduct(PRODUCT_NAME1,generalProduct, null);
        products.putProduct(PRODUCT_NAME1,promotionProduct,promotion);
        products.putProduct(PRODUCT_NAME2,promotionProduct2,promotion2);

        return products;
    }
}
