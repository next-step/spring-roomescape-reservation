package nextstep.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lombok.extern.slf4j.Slf4j;
import nextstep.domain.reservation.dto.ReservationCommandDto.Create;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

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

    @DisplayName("이름이 비어있거나 null이면 실패한다.")
    @NullSource
    @EmptySource
    @ParameterizedTest
    void whenNameIsEmptyOrNull(String name) {
      // given
      ReservationFixtureFactory reservationFixtureFactory = ReservationFixtureFactory.newInstance();
      Create fixtureCreateReq = reservationFixtureFactory.getFixtureCreateReq();

      // when & then

      assertThatThrownBy(() -> reservationCommander.createReservation(
          fixtureCreateReq.toBuilder()
              .name(name)
              .build()
      )).isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("예약의 이름은 공란일 수 없습니다.");
    }
  }
}