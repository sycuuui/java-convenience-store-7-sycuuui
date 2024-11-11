package store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.handler.InputHandler;

import static org.assertj.core.api.Assertions.assertThat;

public class MembershipServiceTest {
    MembershipService membershipService;

    @BeforeEach
    void setup() {
        membershipService = new MembershipService();
    }

    @DisplayName("멤버십 적용 금액 계산 테스트")
    @Test
    void 멤버십_적용_금액_계산_테스트() {
        int totalPurchaseAmount = 5000;
        int totalPromotionAmount = 3000;

        int result = membershipService.calculateMembershipDiscount(totalPurchaseAmount,totalPromotionAmount);
        assertThat(result).isEqualTo(600);
    }

    @DisplayName("멤버십 적용 한도 테스트")
    @Test
    void 멤버십_적용_한도_테스트() {
        int discountPrice = 9000;

        int result = membershipService.checkLimit(discountPrice);
        assertThat(result).isEqualTo(8000);
    }
}
