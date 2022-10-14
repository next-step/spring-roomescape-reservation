package nextstep.schedule.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ScheduleServiceTest extends ServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        initScheduleTable();
        initThemeTable();
    }

    @DisplayName("스케줄을 삭제하려 할 때 ID 에 해당하는 스케줄이 없을 경우 예외가 발생한다.")
    @Test
    void scheduleDeleteException() {
        assertThatThrownBy(() -> scheduleService.deleteById(1L))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("ID 에 해당하는 스케줄이 없습니다.");
    }
}
