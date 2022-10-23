package nextstep.application.reservation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import nextstep.application.reservation.dto.Reservation;
import nextstep.application.schedule.ScheduleQueryService;
import nextstep.application.schedule.dto.ScheduleRes;
import nextstep.domain.reservation.ReservationEntity;
import nextstep.domain.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/truncate.sql")
@SpringBootTest
@DisplayName("Reservation 저장 정책 테스트")
class ReservationCreatePolicyTest {

  @Mock
  private ReservationRepository repository;

  @Mock
  private ScheduleQueryService scheduleQueryService;

  @Test
  void 이미존재하는_예약이_있을시_예외_발생한다() {
    var sut = new ReservationCreatePolicy(List.of(new ReservationCreateExistValidation(repository)));
    var reservation = Reservation.builder()
        .scheduleId(1L)
        .date(LocalDate.now())
        .time(LocalTime.now())
        .name("gump")
        .build();
    given(repository.findReservationsByDateAndTime(any(), any())).willReturn(
        Optional.of(ReservationEntity.builder().build()));
    //when
    //then
    assertThatThrownBy(() -> sut.checkValid(reservation))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 스케줄이_존재하지_않으면_예외_발생한다() {
    var sut = new ReservationCreatePolicy(
        List.of(new ReservationCreateScheduleNotExistValidation(scheduleQueryService)));
    var reservation = Reservation.builder()
        .scheduleId(1L)
        .date(LocalDate.now())
        .time(LocalTime.now())
        .name("gump")
        .build();
    given(scheduleQueryService.getSchedule(reservation.scheduleId())).willReturn(Optional.empty());
    //when
    //then
    assertThatThrownBy(() -> sut.checkValid(reservation))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 스케줄이랑_예약시각이_다르면_예외_발생한다() {
    var sut = new ReservationCreatePolicy(
        List.of(new ReservationCreateScheduleDateTimeNotSameValidation(scheduleQueryService))
    );
    var date = LocalDate.now();
    var time = LocalTime.parse("13:00:00");
    var reservation = Reservation.builder()
        .scheduleId(1L)
        .date(date)
        .time(time)
        .name("gump")
        .build();
    given(scheduleQueryService.getSchedule(reservation.scheduleId())).willReturn(Optional.of(
        ScheduleRes.builder()
            .date(date.minus(1, ChronoUnit.DAYS))
            .time(time)
            .build()
    ));
    //when
    //then
    assertThatThrownBy(() -> sut.checkValid(reservation))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
