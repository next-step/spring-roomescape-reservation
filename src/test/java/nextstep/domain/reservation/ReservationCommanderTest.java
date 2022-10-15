package nextstep.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import nextstep.domain.reservation.dto.ReservationCommandDto.Create;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("예약 커맨드 이벤트 테스트")
class ReservationCommanderTest {

  ReservationCommander reservationCommander = new ReservationCommander(
      // mock
      new ReservationRepository() {
        @Override
        public Reservation save(Reservation reservation) {
          log.debug("객체 저장됨  : {}", reservation);
          return reservation;
        }
      }
  );

  @DisplayName("예약을 생성할 때")
  @Nested
  class CreateTest {

    @DisplayName("성공한다.")
    @Test
    void reservationCreateTest() {
      // given
      ReservationFixtureFactory reservationFixtureFactory = ReservationFixtureFactory.newInstance();
      Reservation expectedFixture = reservationFixtureFactory.getFixture();

      // when
      Reservation reservation = reservationCommander.createReservation(
          reservationFixtureFactory.getFixtureCreateReq()
      );

      // then
      assertThat(reservation.getName()).isEqualTo(expectedFixture.getName());
      assertThat(reservation.getDate()).isEqualTo(expectedFixture.getDate());
      assertThat(reservation.getTime()).isEqualTo(expectedFixture.getTime());
    }
  }
}