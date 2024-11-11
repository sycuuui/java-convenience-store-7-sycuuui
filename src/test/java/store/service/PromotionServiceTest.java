package store.service;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.response.QuantityRes;
import store.handler.InputHandler;
import store.model.PurchaseProducts;
import store.model.product.Products;
import store.util.ProductsUtilTest;
import store.view.Input;

import java.util.Optional;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

public class PromotionServiceTest extends NsTest {
    PromotionService promotionService;

    @BeforeEach
    void setup() {
        //콜라 [일반 상품 - price:1000, quantity:10 , 프로모션 상품 - price:1000, quantity:10, 2+1]
        //오렌지주스 [프로모션 상품 - price:1800, quantity:9, 1+1]
        //물 [일반 상품 - price:500, quantity:5]
        Products products = ProductsUtilTest.getProducts();

        promotionService = new PromotionService(products, new PurchaseProducts(), new InputHandler(new Input(products)));
    }

    @DisplayName("프로모션에 존재하지 않는 상품일 경우 테스트")
    @Test
    void 프로모션에_존재하지_않는_상품일_경우_테스트() {
        boolean result = promotionService.checkPromotion("물");

        assertThat(result).isEqualTo(false);
    }

    @DisplayName("프로모션 기간에 적용되는 프로모션 상품일 경우 테스트")
    @Test
    void 프로모션_기간에_적용되는_프로모션_상품일_경우_테스트() {
        boolean result = promotionService.checkPromotion("콜라");

        assertThat(result).isEqualTo(true);
    }

    @DisplayName("할인 받는 수량과 프로모션 적용 수량 계산 확인 테스트 예시1")
    @Test
    void 할인_받는_수량과_프로모션_적용_수량_계산_확인_테스트_예시1() {
        //콜라 2+1
        Optional<QuantityRes> quantityRes = promotionService.processPromotion("콜라", 7);

        assertThat(quantityRes.get().presentQuantity()).isEqualTo(2);
        assertThat(quantityRes.get().appliedPromotionQuantity()).isEqualTo(6);
    }

    @DisplayName("할인 받는 수량과 프로모션 적용 수량 계산 확인 테스트 예시2")
    @Test
    void 할인_받는_수량과_프로모션_적용_수량_계산_확인_테스트_예시2() {
        //콜라 2+1
        Optional<QuantityRes> quantityRes = promotionService.processPromotion("오렌지주스", 4);

        assertThat(quantityRes.get().presentQuantity()).isEqualTo(2);
        assertThat(quantityRes.get().appliedPromotionQuantity()).isEqualTo(4);
    }

    @DisplayName("프로모션 재고로 인해 적용 받지 못하는 수량이 있을 경우 테스트")
    @Test
    void 프로모션_재고로_인해_적용_받지_못하는_수량이_있을_경우_테스트() {
        assertSimpleTest(() -> {
            runException("Y");
            int purchaseQuantity = 13;
            int appliedPromotionQuantity = 3;

            promotionService.handleUnappliedPromotionQuantity("콜라", purchaseQuantity, appliedPromotionQuantity);

            int unappliedPromotionQuantity = purchaseQuantity - appliedPromotionQuantity;

            assertThat(output()).contains("현재 콜라 " + unappliedPromotionQuantity + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        });
    }

    @DisplayName("프로모션에 추가적으로 수량을 더 받을 수 있는 경우 테스트")
    @Test
    void 프로모션에_추가적으로_수량을_더_받을_수_있는_경우_테스트() {
        assertSimpleTest(() -> {
            runException("Y");
            int appliedAmount = promotionService.applyAdditionalPromotionQuantity("콜라", 5);
            assertThat(output()).contains("현재 콜라은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
            assertThat(appliedAmount).isEqualTo(1);
        });

    }

    @Override
    protected void runMain() {
        promotionService.applyPromotion();
    }
}
