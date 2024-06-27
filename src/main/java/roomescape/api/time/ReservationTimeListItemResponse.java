package roomescape.api.time;

import java.time.LocalTime;
import roomescape.domain.time.ReservationTime;

record ReservationTimeListItemResponse(long id, LocalTime startAt) {

  static ReservationTimeListItemResponse from(ReservationTime time) {
    return new ReservationTimeListItemResponse(time.getId(), time.getStartAt());
  }
}
