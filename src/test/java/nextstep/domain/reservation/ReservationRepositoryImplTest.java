package nextstep.domain.reservation;

import static java.time.LocalTime.now;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import nextstep.domain.reservation.dto.ReservationCommandDto.Delete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationRepositoryImplTest {

  ReservationRepository reservationRepository = new ReservationRepositoryImpl();

  @BeforeEach
  void setting() {
    // for delete all
    reservationRepository = new ReservationRepositoryImpl();
  }

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
    var fixtureFactory = ReservationFixtureFactory.newInstance();
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

  @DisplayName("삭제가 잘 되는지 테스트한다.")
  @Test
  void deleteTest() {
    // given
    var fixtureFactory = ReservationFixtureFactory.newInstance();
    reservationRepository.save(fixtureFactory.getFixture());
    Delete properDeleteReq = fixtureFactory.getFixtureDeleteReq();
    Delete notExistDeleteReq = properDeleteReq.toBuilder()
        .time(properDeleteReq.time().plusMinutes(1))
        .build();

    // when
    boolean expectedTrue = reservationRepository.delete(properDeleteReq);
    boolean expectedFalse = reservationRepository.delete(notExistDeleteReq);
    // then
    assertThat(expectedTrue).isTrue();
    assertThat(expectedFalse).isFalse();
  }
}