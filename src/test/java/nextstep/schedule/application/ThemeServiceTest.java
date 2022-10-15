package nextstep.schedule.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ThemeServiceTest extends ServiceTest {

    @Autowired
    private ThemeService themeService;

    @BeforeEach
    void setUp() {
        initThemeTable();
    }

    @DisplayName("삭제하려는 테마가 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void themeDeleteException() {
        assertThatThrownBy(() -> themeService.deleteById(1L))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("ID 에 해당하는 테마가 없습니다.");
    }
}
