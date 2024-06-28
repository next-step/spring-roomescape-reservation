package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationScheduleNotValid extends RuntimeException {

  public ReservationScheduleNotValid(LocalDate date, LocalTime startAt) {
    super(date.toString() + " " + startAt.toString() + "은 현재 시각 보다 이전입니다. 이후 일정으로 지정해주세요.");
  }
}
