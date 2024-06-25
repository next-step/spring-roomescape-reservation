package roomescape.domain.time;

import java.time.LocalTime;

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
}
