package nextstep.domain.reservation;

import static nextstep.domain.reservation.ReservationFixture.FIXTURE1;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationRepositoryImplTest {

  ReservationRepository reservationRepository = new ReservationRepositoryImpl();

  @DisplayName("예약 객체를 저장하는데 성공한다.")
  @Test
  void saveTest() {
    // given
    var target = FIXTURE1;
    // when
    Reservation saved = reservationRepository.save(target);
    // then
    assertThat(target).isEqualTo(saved);
  }
}