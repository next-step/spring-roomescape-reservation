package nextstep.domain.schedule.service;

import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.theme.service.ThemeService;
import nextstep.exception.ClientException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    private ScheduleService service;

    @Autowired
    private ThemeService themeService;

    @DisplayName("존재 하지 않는 테마 ID로 생성 요청시 클라이언트 예외가 발생한다.")
    @Test
    void create_WithNotExistedThemeId_ThrowException() {
        // given
        Schedule schedule = new Schedule(null, 1L, LocalDate.of(2022, 8, 11), LocalTime.of(13, 10));

        // when, then
        assertThrows(ClientException.class, () -> service.create(schedule));
    }
}
