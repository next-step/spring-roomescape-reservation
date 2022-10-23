package nextstep.domain.theme.service;

import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.service.ReservationService;
import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.schedule.service.ScheduleService;
import nextstep.domain.theme.model.Theme;
import nextstep.exception.ClientException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ThemeServiceTest {
    @Autowired
    private ThemeService themeService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ReservationService reservationService;

    @DisplayName("예약이 이미 존재하는 테마를 제거할 경우 클라이언트 예외가 발생한다.")
    @Test
    void remove_WithExistedReservation_ThrowException() {
        // given
        Long themeId = themeService.create(new Theme(null, "테마", "설명", 20_000L));
        Long scheduleId = scheduleService.create(new Schedule(null, themeId, LocalDate.of(2022, 10, 10), LocalTime.of(10, 10)));
        reservationService.create(new Reservation(null, "예약", scheduleId));

        // when, then
        assertThrows(ClientException.class, () -> themeService.remove(themeId));
    }
}
