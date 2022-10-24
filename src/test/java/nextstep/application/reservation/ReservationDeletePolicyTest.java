package nextstep.application.reservation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.application.reservation.dto.ReservationDeleteValidationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/truncate.sql")
@SpringBootTest
@DisplayName("Reservation 삭제 정책 테스트")
class ReservationDeletePolicyTest {

  @Autowired
  private ReservationDeletePolicy policy;

  @Test
  void reservation_존재하지_않으면_예외가_발생한다() {
    //given
    var validationDto = ReservationDeleteValidationDto.builder()
        .date(LocalDate.now())
        .time(LocalTime.now())
        .build();
    //when
    //then
    assertThatThrownBy(() -> policy.checkValid(validationDto))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
