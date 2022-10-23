package nextstep.application.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.application.reservation.dto.Reservation;
import nextstep.application.schedule.ScheduleService;
import nextstep.application.schedule.dto.Schedule;
import nextstep.application.themes.ThemeService;
import nextstep.application.themes.dto.Theme;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/truncate.sql")
@SpringBootTest
@DisplayName("RoomEscape 서비스 통합 테스트")
class ReservationServiceIntegrationTest {

  @Autowired
  private ReservationService sut;

  @Autowired
  private ScheduleService scheduleService;

  @Autowired
  private ThemeService themeService;

  @Test
  void 에약_생성한다() {
    //given
    var date = LocalDate.now();
    var time = LocalTime.now();
    var themeId = 테마_생성된다();
    var scheduleId = 스케쥴_생성된다(themeId, date, time);
    var reservation = Reservation.builder()
        .scheduleId(scheduleId)
        .date(date)
        .time(time)
        .name("gump")
        .build();
    //when
    var actual = sut.create(reservation);
    //then
    assertThat(actual).isNotNull();
  }

  @Test
  void 예약_목록_조회한다() {
    //given
    var date = LocalDate.now();
    var time = LocalTime.now();
    var themeId = 테마_생성된다();
    var scheduleId = 스케쥴_생성된다(themeId, date, time);
    var reservation = Reservation.builder()
        .scheduleId(scheduleId)
        .date(date)
        .time(time)
        .name("gump")
        .build();
    sut.create(reservation);
    //when
    var reservations = sut.findReservations(reservation.date());
    //then
    assertThat(reservations).hasSize(1);
  }

  @Test
  void 예약_삭제_된다() {
    //given
    var date = LocalDate.now();
    var time = LocalTime.now();
    var themeId = 테마_생성된다();
    var scheduleId = 스케쥴_생성된다(themeId, date, time);
    var reservation = Reservation.builder()
        .scheduleId(scheduleId)
        .date(date)
        .time(time)
        .name("gump")
        .build();
    sut.create(reservation);
    //when
    sut.removeReservation(date, time);
    var reservations = sut.findReservations(reservation.date());
    //then
    assertThat(reservations).hasSize(0);
  }

  private Long 스케쥴_생성된다(Long themeId, LocalDate date, LocalTime time) {
    var schedule = Schedule.builder()
        .themeId(themeId)
        .date(date)
        .time(time)
        .build();
    return scheduleService.create(schedule);
  }

  private Long 테마_생성된다() {
    var theme = Theme.builder()
        .name("테마이름")
        .desc("테마설명")
        .price(BigDecimal.valueOf(22000))
        .build();
    return themeService.create(theme);
  }
}
