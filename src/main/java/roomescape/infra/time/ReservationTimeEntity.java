package roomescape.infra.time;

import java.time.LocalTime;
import roomescape.domain.time.ReservationTime;

class ReservationTimeEntity {
  private long id;
  private LocalTime startAt;

  public ReservationTimeEntity(long id, LocalTime startAt) {
    this.id = id;
    this.startAt = startAt;
  }

  public ReservationTime toDomain() {
    return new ReservationTime(id, startAt);
  }
  public long getId() {
    return id;
  }

  public LocalTime getStartAt() {
    return startAt;
  }
}
