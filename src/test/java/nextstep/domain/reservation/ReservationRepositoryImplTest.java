package nextstep.domain.reservation;

import static java.time.LocalTime.now;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationRepositoryImplTest {

  ReservationRepository reservationRepository = new ReservationRepositoryImpl();

  @DisplayName("예약 객체를 저장하는데 성공한다.")
  @Test
  void saveTest() {
    // given
    var target = ReservationFixtureFactory.newInstance().getFixture();
    // when
    Reservation saved = reservationRepository.save(target);
    // then
    assertThat(target).isEqualTo(saved);
  }

  @DisplayName("예약 객체를 전체 조회하는 데 성공한다.")
  @Test
  void findAllTest() {
    // given
    ReservationFixtureFactory fixtureFactory = ReservationFixtureFactory.newInstance();
    var reservations =
        ReservationFixtureFactory.newInstance()
            .getCustomFixtures(
                // now() + 1day and +1minute ~ +10minute
                now().plusMinutes(1), now().plusMinutes(10)
            );

    reservations.forEach(
        reservation -> reservationRepository.save(reservation)
    );
    int expectedSize = reservations.size();

    // when
    List<Reservation> findAll = reservationRepository.findAll(
        fixtureFactory.getFixtureFindCondition() // now() + 1 day
    );

    // then
    assertThat(findAll).hasSize(expectedSize);
  }
}