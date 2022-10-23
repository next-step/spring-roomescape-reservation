package nextstep.domain.reservation.service;

import nextstep.domain.Identity;
import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.schedule.service.ScheduleService;
import nextstep.domain.theme.model.Theme;
import nextstep.domain.theme.service.ThemeService;
import nextstep.exception.ClientException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {
    @Autowired
    private ReservationService service;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ThemeService themeService;

    @DisplayName("존재하지 않는 일정으로 예약을 생성할 경우 예외가 발생한다.")
    @Test
    void create_WithNotExistedSchedule_ThrowException() {
        // given
        Long notExistedScheduleId = 1L;
        Reservation reservation = new Reservation(null, "example", notExistedScheduleId);

        // when, then
        assertThrows(ClientException.class, () -> service.create(reservation));
    }

    @DisplayName("이미 생성된 예약과 같은 일정으로 예약을 생성할 경우 예외가 발생한다.")
    @Test
    void create_WithDuplicatedSchedule_ThrowException() {
        // given
        Long themeId = themeService.create(new Theme(null, "name", "description", 22_000L));
        Long scheduleId = scheduleService.create(new Schedule(null, themeId, LocalDate.of(2022, 10, 4), LocalTime.of(10, 10)));

        Reservation reservation = new Reservation(null, "example", scheduleId);
        service.create(reservation);

        // when, then
        assertThrows(ClientException.class, () -> service.create(reservation));
    }

    @DisplayName("존재하지 않는 일정에 대한 예약을 삭제할 경우 예외가 발생한다.")
    @Test
    void removeByScheduleId_WithNotExistedSchedule_ThrowException() {
        // given
        Long notExistedScheduleId = 0L;

        // when, then
        assertThrows(ClientException.class, () -> service.removeByScheduleId(notExistedScheduleId));
    }
}
