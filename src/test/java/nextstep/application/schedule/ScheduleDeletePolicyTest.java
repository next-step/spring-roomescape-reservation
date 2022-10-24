package nextstep.application.schedule;

import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import nextstep.application.reservation.ReservationQueryService;
import nextstep.application.reservation.dto.ReservationRes;
import nextstep.application.schedule.dto.ScheduleDeleteValidationDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/truncate.sql")
@SpringBootTest
@DisplayName("스케쥴 삭제 정책 테스트")
class ScheduleDeletePolicyTest {

  @Mock
  private ReservationQueryService reservationQueryService;

  @Test
  void 예약되어있는_스케쥴_삭제시_예외가_발생한다() {
    //given
    var sut = new ScheduleDeletePolicy(
        List.of(new ScheduleDeleteAssignedReservationValidation(reservationQueryService)));
    var scheduleId = 1L;
    given(reservationQueryService.getReservationByScheduleId(scheduleId)).willReturn(
        Optional.of(ReservationRes.builder().build()));
    var validationDto = ScheduleDeleteValidationDto.builder()
        .id(scheduleId)
        .build();
    //when
    //then
    Assertions.assertThatThrownBy(() -> sut.checkValid(validationDto))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
