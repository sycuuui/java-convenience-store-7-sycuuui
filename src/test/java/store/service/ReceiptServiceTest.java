package store.service;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.response.QuantityRes;
import store.model.PurchaseProducts;
import store.model.product.Products;
import store.util.ProductsUtilTest;
import store.view.Output;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

public class ReceiptServiceTest extends NsTest {
    ReceiptService receiptService;

    @BeforeEach
    void setup() {
        //콜라 [일반 상품 - price:1000, quantity:10 , 프로모션 상품 - price:1000, quantity:10, 2+1]
        //오렌지주스 [프로모션 상품 - price:1800, quantity:9, 1+1]
        //물 [일반 상품 - price:500, quantity:5]
        Products products = ProductsUtilTest.getProducts();

        PurchaseProducts purchaseProducts = new PurchaseProducts();
        purchaseProducts.putInitialPurchaseDetails("콜라",4);
        purchaseProducts.putInitialPurchaseDetails("오렌지주스", 4);
        purchaseProducts.putInitialPurchaseDetails("물",3);

        purchaseProducts.putPurchasePromotionDetail("콜라",new QuantityRes(1,3));
        purchaseProducts.putPurchasePromotionDetail("오렌지주스",new QuantityRes(2,4));

        MembershipService membershipService = new MembershipService();
        membershipService.setApply(true);

        receiptService = new ReceiptService(products,purchaseProducts,membershipService,new Output());
    }

    @DisplayName("구매하는 상품 출력 테스트")
    @Test
    void 구매하는_상품_출력_테스트() {
        assertSimpleTest(() -> {
            receiptService.processTotalPurchase();
            assertThat(output().replaceAll("\\s", "")).contains("콜라"+"4"+"4,000")
                    .contains("오렌지주스"+"4"+"7,200")
                    .contains("물"+"3"+"1,500");
        });
    }

    @DisplayName("증정_상품_출력_테스트")
    @Test
    void 증정_상품_출력_테스트() {
        assertSimpleTest(() -> {
            receiptService.processTotalDiscount();
            assertThat(output().replaceAll("\\s", "")).contains("콜라"+"1")
                    .contains("오렌지주스"+"2");
        });
    }

    @DisplayName("총구매액 출력 테스트")
    @Test
    void 총구매액_출력_테스트() {
        assertSimpleTest(() -> {
            receiptService.processReceipt();
            assertThat(output().replaceAll("\\s", "")).contains("총구매액"+"11"+"12,700")
                    .contains("행사할인"+"-"+"4,600")
                    .contains("멤버십할인"+"-"+"750")
                    .contains("내실돈"+"7,350");
        });
    }

    @Override
    protected void runMain() {
        receiptService.processReceipt();
    }
}
