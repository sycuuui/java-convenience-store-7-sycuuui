package store.validator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;
import store.Application;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseValidatorTest extends NsTest {
    @Test
    void 대괄호_없이_입력한_경우_에러_테스트() {
        assertSimpleTest(() -> {
            runException("컵라면-12, 초코바-1");
            assertThat(output()).contains("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 입력_순서_맞지_않게_입력한_경우_에러_테스트() {
        assertSimpleTest(() -> {
            runException("[10-컵라면]");
            assertThat(output()).contains("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 빈_값이_있을_경우_에러_테스트() {
        assertSimpleTest(() -> {
            runException("[컵라면-10],[초코바-5],,");
            assertThat(output()).contains("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 존재하지_않는_값이_있을_경우_에러_테스트() {
        assertSimpleTest(() -> {
            runException("[라면-10],[초코바-5]");
            assertThat(output()).contains("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 구매_수량이_재고_수량을_초과한_경우_에러_테스트() {
        assertSimpleTest(() -> {
            runException("[콜라-22]");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
