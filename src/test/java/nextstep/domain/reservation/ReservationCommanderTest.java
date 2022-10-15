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
      var name = "성시형";
      var date = LocalDate.now().plusDays(1);
      var time = LocalTime.now();

      var createReq = Create.builder()
          .date(date)
          .time(time)
          .name(name)
          .build();

      // when
      Reservation reservation = reservationCommander.createReservation(
          createReq
      );

      // then
      assertThat(reservation.getName()).isEqualTo(name);
      assertThat(reservation.getDate()).isEqualTo(date);
      assertThat(reservation.getTime()).isEqualTo(time);
    }
  }
}