package nextstep.application.reservation;

import lombok.RequiredArgsConstructor;
import nextstep.application.reservation.dto.Reservation;
import nextstep.application.schedule.ScheduleQueryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationCreateScheduleNotExistValidation implements ReservationCreateValidation {

  private final ScheduleQueryService scheduleQueryService;

  @Override
  public void checkValid(Reservation reservation) {
    var schedule = scheduleQueryService.getSchedule(reservation.scheduleId());
    if (schedule.isEmpty()) {
      throw new IllegalArgumentException(String.format("스케쥴이 존재하지 않습니다. 스케쥴ID: %s", reservation.scheduleId()));
    }
  }

  @Override
  public int priority() {
    return 2;
  }
}
