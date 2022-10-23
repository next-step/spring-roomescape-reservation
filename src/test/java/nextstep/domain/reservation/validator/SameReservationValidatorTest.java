package nextstep.domain.reservation.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import nextstep.domain.reservation.Reservation;
import nextstep.domain.reservation.ReservationFinder;
import nextstep.domain.reservation.ReservationFixtureFactory;
import nextstep.domain.reservation.dto.ReservationFindCondition;
import nextstep.exception.ReservationIllegalArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SameReservationValidatorTest {

  private final SameReservationValidator validator =
      new SameReservationValidator(
          new ReservationFinder() {
            final ReservationFixtureFactory reservationFixtureFactory = ReservationFixtureFactory.newInstance();
            @Override
            public List<Reservation> findAll(ReservationFindCondition condition) {
              /*
               * reservationFixtureFactory.getFixtureFindCondition(); 을 통해 받아온 condition인 경우 1개 결과 리턴.
               * 아니면 EmptyList 리턴
               */
              var fixture = reservationFixtureFactory.getFixture();

              if (condition.date().isEqual(fixture.getDate())) {
                return List.of(fixture);
              }

              return Collections.emptyList();
            }
          }
      );

  @DisplayName("같은 일자의 예약이 없다면 validation을 통과한다.")
  @Test
  void sameReservationValidateTest() {
    // given
    var reservationFixtureFactory = ReservationFixtureFactory.newInstance();
    var alreadyExistReservation = reservationFixtureFactory.getFixtureCreateReq();
    var notExistReservation = alreadyExistReservation.toBuilder()
        .date(alreadyExistReservation.date().plusDays(1))
        .build();

    // when & then
    assertThatCode(() -> validator.validate(notExistReservation))
        .doesNotThrowAnyException();
  }

  @DisplayName("같은 일자의 예약이 있다면 에외가 발생한다.")
  @Test
  void whenExistSameDateReservation() {
    // given
    var reservationFixtureFactory = ReservationFixtureFactory.newInstance();
    var createReq = reservationFixtureFactory.getFixtureCreateReq();
    // when & then
    assertThatThrownBy(() -> validator.validate(createReq))
        .isInstanceOf(ReservationIllegalArgumentException.class)
        .hasMessageContaining("해당 일시엔 이미 예약이 존재합니다.");
  }
}