package nextstep.domain.reservation.dto;

import static nextstep.domain.reservation.Reservation.adjustReservationTimeRule;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

public record ReservationFindCondition(
    LocalDate date,
    LocalTime time
) {

  @Builder(toBuilder = true)
  @Jacksonized
  public ReservationFindCondition {
    time = adjustReservationTimeRule(time);
  }
}
