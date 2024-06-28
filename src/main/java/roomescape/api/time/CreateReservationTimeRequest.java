package roomescape.api.time;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import roomescape.domain.time.CreateReservationTime;

record CreateReservationTimeRequest(@NotNull LocalTime startAt) {
  CreateReservationTime toDomain() {
    return new CreateReservationTime(startAt);
  }
}
