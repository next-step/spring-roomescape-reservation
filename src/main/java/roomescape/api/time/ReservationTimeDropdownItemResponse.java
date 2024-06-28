package roomescape.api.time;

import java.time.LocalTime;
import roomescape.domain.time.ReservationTime;

record ReservationTimeDropdownItemResponse(long id, LocalTime startAt) {
  static ReservationTimeDropdownItemResponse from(ReservationTime time) {
    return new ReservationTimeDropdownItemResponse(time.getId(), time.getStartAt());
  }
}
