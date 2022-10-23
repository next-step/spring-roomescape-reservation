package nextstep.domain.schedule.service;

import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.service.ReservationService;
import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.theme.model.Theme;
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
    private ScheduleService scheduleService;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ReservationService reservationService;

    @DisplayName("존재 하지 않는 테마 ID로 생성 요청시 클라이언트 예외가 발생한다.")
    @Test
    void create_WithNotExistedThemeId_ThrowException() {
        // given
        Long notExistedThemeId = 0L;
        Schedule schedule = new Schedule(null, notExistedThemeId, LocalDate.of(2022, 8, 11), LocalTime.of(13, 10));

        // when, then
        assertThrows(ClientException.class, () -> scheduleService.create(schedule));
    }

    @DisplayName("예약이 존재하는 일정을 제거할 경우 클라이언트 예외가 발생한다.")
    @Test
    void remove_WithExistedReservation_ThrowException() {
        // given
        Long themeId = themeService.create(new Theme(null, "테마", "설명", 20_000L));
        Long scheduleId = scheduleService.create(new Schedule(null, themeId, LocalDate.of(2022, 10, 10), LocalTime.of(10, 10)));
        reservationService.create(new Reservation(null, "예약", scheduleId));

        // when, then
        assertThrows(ClientException.class, () -> scheduleService.remove(scheduleId));
    }
}
