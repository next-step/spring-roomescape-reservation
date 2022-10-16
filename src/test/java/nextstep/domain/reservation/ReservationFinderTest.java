package nextstep.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import nextstep.domain.reservation.dto.ReservationFindCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationFinderTest {

  ReservationFinder reservationFinder = new ReservationFinder(new ReservationRepository() {
    @Override
    public List<Reservation> findAll(ReservationFindCondition condition) {
      /*
       * reservationFixtureFactory.getFixtureFindCondition(); 을 통해 받아온 condition인 경우 1개 결과 리턴.
       * 아니면 EmptyList 리턴
       */
      var reservationFixtureFactory = ReservationFixtureFactory.newInstance();
      var fixture = reservationFixtureFactory.getFixture();

      if (condition.getDate().isEqual(fixture.getDate())) {
        return List.of(fixture);
      }

      return Collections.emptyList();
    }
  });


  @DisplayName("조건에 따라 예약을 찾아오는지 확인한다.")
  @Test
  void findByConditionTest() {
    // given
    var reservationFixtureFactory = ReservationFixtureFactory.newInstance();
    var fixtureFindCondition = reservationFixtureFactory.getFixtureFindCondition();
    // when
    var byCondition = reservationFinder.findAll(fixtureFindCondition);
    // then
    assertThat(byCondition).hasSizeGreaterThan(0);
  }
}