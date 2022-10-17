package nextstep.application.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import nextstep.application.reservation.RoomEscapeService;
import nextstep.application.reservation.dto.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/truncate.sql")
@SpringBootTest
@DisplayName("RoomEscape 서비스 통합 테스트")
class RoomEscapeServiceIntegrationTest {

  @Autowired
  private RoomEscapeService sut;

  @Test
  void 에약_생성한다() {
    //given
    var reservation = Reservation.builder()
        .date(LocalDate.now())
        .time("13:00")
        .name("gump")
        .build();
    //when
    var actual = sut.create(reservation);
    //then
    assertThat(actual).isNotNull();
  }

  @Test
  void 예약_목록_조회한다() {
    //given
    var reservation = Reservation.builder()
        .date(LocalDate.now())
        .time("13:00")
        .name("gump")
        .build();
    sut.create(reservation);
    //when
    var reservations = sut.findReservations(reservation.date());
    //then
    assertThat(reservations).hasSize(1);
  }

  @Test
  void 예약_삭제_된다() {
    //given
    var reservation = Reservation.builder()
        .date(LocalDate.now())
        .time("13:00")
        .name("gump")
        .build();
    sut.create(reservation);
    //when
    sut.removeReservation(reservation.date(), reservation.time());
    var reservations = sut.findReservations(reservation.date());
    //then
    assertThat(reservations).hasSize(0);
  }
}
