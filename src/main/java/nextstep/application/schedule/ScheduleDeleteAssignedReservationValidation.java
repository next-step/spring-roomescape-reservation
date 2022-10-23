package nextstep.application.schedule;

import lombok.RequiredArgsConstructor;
import nextstep.application.reservation.ReservationQueryService;
import nextstep.application.schedule.dto.ScheduleDeleteValidationDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleDeleteAssignedReservationValidation implements ScheduleDeleteValidation {

  private final ReservationQueryService reservationQueryService;

  @Override
  public void checkValid(ScheduleDeleteValidationDto validationDto) {
    var reservation = reservationQueryService.getReservationByScheduleId(validationDto.id());
    if (reservation.isPresent()) {
      var reservationRes = reservation.get();
      throw new IllegalArgumentException(String.format("예약이 되어있어 삭제가 불가능합니다. 예약ID: %s", reservationRes.id()));
    }
  }

  @Override
  public int priority() {
    return 1;
  }
}
