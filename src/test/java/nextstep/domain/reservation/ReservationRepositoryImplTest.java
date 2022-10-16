package nextstep.domain.reservation;

import static java.time.LocalTime.now;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import nextstep.domain.reservation.dto.ReservationCommandDto.Delete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ReservationRepositoryImplTest {

  @Autowired
  ReservationRepository reservationRepository;

  @Autowired
  JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
    jdbcTemplate.update("delete from reservation");
  }

  @DisplayName("예약 객체를 저장하는데 성공한다.")
  @Test
  void saveTest() {
    // given
    var target = ReservationFixtureFactory.newInstance().getFixtureCreateReq();
    // when
    Reservation saved = reservationRepository.save(target);
    // then
    assertThat(target.name()).isEqualTo(saved.getName().value());
    assertThat(target.date()).isEqualTo(saved.getDate());
    assertThat(target.time()).isEqualTo(saved.getTime());
  }

  @DisplayName("예약 객체를 전체 조회하는 데 성공한다.")
  @Test
  void findAllTest() {
    // given
    var fixtureFactory = ReservationFixtureFactory.newInstance();
    var reservations =
        ReservationFixtureFactory.newInstance()
            .getCustomFixturesCreateReq(
                // now() + 1day and +1minute ~ +10minute
                now().plusMinutes(1), now().plusMinutes(10)
            );

    int expectedSize = reservations.size();
    reservations.forEach(
        reservation -> reservationRepository.save(reservation)
    );

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
    reservationRepository.save(fixtureFactory.getFixtureCreateReq());
    Delete properDeleteReq = fixtureFactory.getFixtureDeleteReq();
    Delete notExistDeleteReq = properDeleteReq.toBuilder()
        .date(properDeleteReq.date().plusDays(1))
        .build();

    // when
    boolean expectedTrue = reservationRepository.delete(properDeleteReq);
    boolean expectedFalse = reservationRepository.delete(notExistDeleteReq);
    // then
    assertThat(expectedTrue).isTrue();
    assertThat(expectedFalse).isFalse();
  }
}