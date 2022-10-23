package nextstep.domain.schedule.dto;

import static nextstep.domain.reservation.Reservation.adjustReservationTimeRule;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

public record ScheduleFindCondition(
    Long themeId,
    LocalDate date,
    LocalTime time
) {

  public static final ScheduleFindCondition EMPTY = new ScheduleFindCondition(null, null, null);

  @Builder(toBuilder = true)
  @Jacksonized
  public ScheduleFindCondition {
    time = adjustReservationTimeRule(time);
  }

  public boolean existThemeId() {
    return themeId != null;
  }

  public boolean existDate() {
    return date != null;
  }
}
