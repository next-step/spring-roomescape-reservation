package roomescape.infra.time;

import java.time.LocalTime;
import roomescape.domain.time.ReservationTime;

class ReservationTimeEntity {
  private long id;
  private String startAt;

  public ReservationTimeEntity(long id, String startAt) {
    this.id = id;
    this.startAt = startAt;
  }

  public ReservationTime toDomain() {
    return new ReservationTime(id, LocalTime.parse(startAt));
  }
  public long getId() {
    return id;
  }

  public String getStartAt() {
    return startAt;
  }
}
