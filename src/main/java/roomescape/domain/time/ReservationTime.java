package roomescape.domain.time;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.ReservationScheduleNotValid;

public class ReservationTime {

  private long id;
  private LocalTime startAt;

  public ReservationTime(long id, LocalTime startAt) {
    this.id = id;
    this.startAt = startAt;
  }

  public ReservationTime(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public LocalTime getStartAt() {
    return startAt;
  }

  public void validDateTime(LocalDate date) {
    if (date.isBefore(LocalDate.now())) {
      throw new ReservationScheduleNotValid(date, startAt);
    }
    if (date.isEqual(LocalDate.now()) && startAt.isBefore(LocalTime.now())) {
      throw new ReservationScheduleNotValid(date, startAt);
    }
  }
}
