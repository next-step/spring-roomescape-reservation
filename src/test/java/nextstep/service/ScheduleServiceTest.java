package nextstep.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import nextstep.application.ReservationService;
import nextstep.application.ScheduleService;
import nextstep.application.ThemeService;
import nextstep.exception.ScheduleException;
import nextstep.presentation.dto.ScheduleRequest;
import nextstep.presentation.dto.ScheduleResponse;
import nextstep.presentation.dto.ThemeRequest;
import nextstep.presentation.dto.ThemeResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ScheduleService scheduleService;

    @AfterEach
    void tearDown() {
        reservationService.cancelAll();
        scheduleService.cancelAll();
        themeService.deleteAll();
    }

    @DisplayName("테마별 스케줄을 생성한다.")
    @Test
    void make() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("열쇠공이", "열쇠공이의 이중생활", 25000);
        Long themeId = themeService.create(themeRequest);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, "2022-08-11", "16:00");

        // when
        Long scheduleId = scheduleService.make(scheduleRequest);

        // then
        assertThat(scheduleId).isNotNull();
    }

    @DisplayName("테마별 스케줄 목록을 조회한다.")
    @Test
    void checkAll() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("열쇠공이", "열쇠공이의 이중생활", 25000);
        Long themeId = themeService.create(themeRequest);

        ScheduleRequest scheduleRequest1 = new ScheduleRequest(themeId, "2022-08-11", "16:00");
        ScheduleRequest scheduleRequest2 = new ScheduleRequest(themeId, "2022-08-11", "20:00");
        scheduleService.make(scheduleRequest1);
        scheduleService.make(scheduleRequest2);

        ThemeResponse themeResponse = new ThemeResponse(null, "열쇠공이", "열쇠공이의 이중생활", 25000);
        List<ScheduleResponse> expected = List.of(
            new ScheduleResponse(null, themeResponse, "2022-08-11", "16:00"),
            new ScheduleResponse(null, themeResponse, "2022-08-11", "20:00")
        );

        // when
        List<ScheduleResponse> responses = scheduleService.checkAll(themeId, "2022-08-11");

        // then
        assertThat(responses).hasSize(2);
        assertThat(responses)
            .usingRecursiveComparison()
            .ignoringFields("id", "theme.id")
            .isEqualTo(expected);
    }

    @DisplayName("스케줄을 삭제한다.")
    @Test
    void cancel_success() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("열쇠공이", "열쇠공이의 이중생활", 25000);
        Long themeId = themeService.create(themeRequest);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, "2022-08-11", "16:00");
        Long scheduleId = scheduleService.make(scheduleRequest);

        reservationService.cancelAll();

        // when
        // then
        assertThatCode(() -> scheduleService.cancel(scheduleId)).doesNotThrowAnyException();
    }

    @DisplayName("예약과 관련 있는 스케줄은 삭제할 수 없다.")
    @Test
    void cancel_fail1() {
        // given
        ThemeRequest themeRequest = new ThemeRequest("열쇠공이", "열쇠공이의 이중생활", 25000);
        Long themeId = themeService.create(themeRequest);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, "2022-08-11", "16:00");
        Long scheduleId = scheduleService.make(scheduleRequest);

        // when
        // then
        assertThatThrownBy(() -> scheduleService.cancel(scheduleId))
            .isInstanceOf(ScheduleException.class)
            .hasMessage("스케줄을 삭제할 수 없습니다.");
    }

    @DisplayName("존재하지 않는 스케줄은 삭제할 수 없다.")
    @Test
    void cancel_fail2() {
        // given
        // when
        // then
        assertThatThrownBy(() -> scheduleService.cancel(Long.MAX_VALUE))
            .isInstanceOf(ScheduleException.class)
            .hasMessage("존재하지 않는 스케줄입니다.");
    }
}
