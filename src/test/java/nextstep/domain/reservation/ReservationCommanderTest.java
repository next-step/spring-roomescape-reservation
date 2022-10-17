package nextstep.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import nextstep.domain.reservation.Reservation.Name;
import nextstep.domain.reservation.dto.ReservationCommandDto.Create;
import nextstep.domain.reservation.dto.ReservationCommandDto.Delete;
import nextstep.exception.ReservationIllegalArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("예약 커맨드 이벤트 테스트")
class ReservationCommanderTest {

  ReservationFixtureFactory reservationFixtureFactory = ReservationFixtureFactory.newInstance();
  ReservationCommander reservationCommander = new ReservationCommander(

      // mock
      new ReservationRepository() {
        @Override
        public Reservation save(Create createReq) {
          log.debug("객체 저장됨  : {}", createReq);
          return Reservation.builder()
              .name(Name.of(createReq.name()))
              .date(createReq.date())
              .time(createReq.time())
              .build();
        }

        @Override
        public boolean delete(Delete deleteReq) {
          return reservationFixtureFactory.getFixtureDeleteReq().equals(deleteReq);
        }
      }, List.of()
  );

  @DisplayName("예약을 생성할 때")
  @Nested
  class CreateTest {

    @DisplayName("성공한다.")
    @Test
    void reservationCreateTest() {
      // given
      var reservationFixtureFactory = ReservationFixtureFactory.newInstance();
      var expectedFixture = reservationFixtureFactory.getFixture();

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

  @DisplayName("예약을 삭제할 때")
  @Nested
  class DeleteTest {

    @DisplayName("성공한다.")
    @Test
    void deleteReservation() {
      // given
      var deleteReq = reservationFixtureFactory.getFixtureDeleteReq();
      // when & then
      assertThatCode(() -> reservationCommander.deleteReservation(deleteReq)).doesNotThrowAnyException();
    }

    @DisplayName("해당하는 일시의 예약이 없으면 예외가 발생한다.")
    @Test
    void whenNotExistReservation() {
      // given
      var deleteReq = reservationFixtureFactory.getFixtureDeleteReq();
      var notExistDeleteReq = deleteReq.toBuilder()
          .date(deleteReq.date().plusDays(1))
          .build();
      // when & then
      assertThatThrownBy(() -> reservationCommander.deleteReservation(notExistDeleteReq))
          .isInstanceOf(ReservationIllegalArgumentException.class)
          .hasMessageContaining("해당 일시에는 예약이 없습니다. 요청받은 일자");
    }
  }
}