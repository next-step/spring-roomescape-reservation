package nextstep.domain.reservation.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.domain.reservation.ReservationFixtureFactory;
import nextstep.domain.reservation.dto.ReservationCommandDto.Create;
import nextstep.exception.ReservationIllegalArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("예약 날짜와 시간을 확인할 때")
class DateAndTimeValidatorTest {

  DateAndTimeValidator validator = new DateAndTimeValidator();

  @DisplayName("예약 시간이 적절하면 통과한다.")
  @Test
  void pass() {
    // given
    ReservationFixtureFactory reservationFixtureFactory = ReservationFixtureFactory.newInstance();
    // + 1일 후 정상 예약
    Create fixtureCreateReq = reservationFixtureFactory.getFixtureCreateReq();

    // when & then
    assertThatCode(() -> validator.validate(
        fixtureCreateReq
    )).doesNotThrowAnyException();
  }

  @DisplayName("예약 날짜가 지났으면 예외가 발생한다.")
  @Test
  void whenPastDate() {
    // given
    ReservationFixtureFactory reservationFixtureFactory = ReservationFixtureFactory.newInstance();
    Create fixtureCreateReq = reservationFixtureFactory.getFixtureCreateReq();

    var pastDate = LocalDate.now().minusDays(1);

    // when & then
    assertThatThrownBy(() -> validator.validate(
        fixtureCreateReq.toBuilder()
            .date(pastDate)
            .build()
    )).isInstanceOf(ReservationIllegalArgumentException.class)
        .hasMessageContaining("이미 지난 날짜에 대해 예약할 수 없습니다. 현재 날짜 : ");
  }

  @DisplayName("예약 날짜가 동일하고, 시간이 과거라면 예외가 발생한다.")
  @Test
  void passPastTime() {
    // given
    ReservationFixtureFactory reservationFixtureFactory = ReservationFixtureFactory.newInstance();
    Create fixtureCreateReq = reservationFixtureFactory.getFixtureCreateReq();

    var pastTime = LocalTime.now().minusSeconds(1);
    var today = LocalDate.now();

    // when & then
    assertThatThrownBy(() -> validator.validate(
        fixtureCreateReq.toBuilder()
            .date(today)
            .time(pastTime)
            .build()
    )).isInstanceOf(ReservationIllegalArgumentException.class)
        .hasMessageContaining("이미 지난 시간에 대해 예약할 수 없습니다. 현재 시간 : ");
  }
}