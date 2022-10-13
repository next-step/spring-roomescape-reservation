package nextstep.domain;

import static nextstep.domain.Fixtures.themePrice;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ThemePriceTest {

    @DisplayName("테마금액이 null 일 경우 예외가 발생한다.")
    @Test
    void nullException() {
        assertThatThrownBy(() -> new ThemePrice(null))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("올바르지 않은 테마금액입니다.");
    }

    @DisplayName("테마금액이 음수일 경우 예외가 발생한다.")
    @Test
    void negativeException() {
        assertThatThrownBy(() -> themePrice(-1))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("테마금액은 0 보다 작을 수 없습니다.");
    }
}
