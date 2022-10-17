package nextstep.application.reservation;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import nextstep.application.reservation.ReservationPolicy;
import nextstep.application.reservation.RoomEscapeService;
import nextstep.application.reservation.dto.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/truncate.sql")
@SpringBootTest
@DisplayName("Reservation 정책 테스트")
class ReservationPolicyTest {

  @Autowired
  ReservationPolicy policy;

  @Autowired
  RoomEscapeService service;

  @Test
  void reservation_validation_통과한다() {
    //given
    var reservation = Reservation.builder()
        .date(LocalDate.now())
        .time("13:00")
        .name("gump")
        .build();
    //when
    //then
    assertThatCode(() -> policy.checkValid(reservation)).doesNotThrowAnyException();
  }

  @Test
  void reservation_validation_예외_발생한다() {
    var reservation = Reservation.builder()
        .date(LocalDate.now())
        .time("13:00")
        .name("gump")
        .build();
    //when
    service.create(reservation);
    //then
    assertThatThrownBy(() -> policy.checkValid(reservation))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
