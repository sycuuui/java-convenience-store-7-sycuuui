package store.validator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.Application;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

public class QuestionValidatorTest  extends NsTest {
    @DisplayName("Y,N 이외의 답변을 한 경우 에러 테스트")
    @Test
    void Y_N_이외의_답변을_한_경우_에러_테스트() {
        assertSimpleTest(() -> {
            runException("[콜라-10]","Y","NN");
            assertThat(output()).contains("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        });
    }

    @DisplayName("빈 답변을 한 경우 에러 테스트")
    @Test
    void 빈_답변을_한_경우_에러_테스트() {
        assertSimpleTest(() -> {
            runException("[콜라-10]","Y"," ");
            assertThat(output()).contains("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        });
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
