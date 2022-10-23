package nextstep.domain.reservation.dto;

import lombok.Builder;

public record ReservationFindCondition(
    Long scheduleId
) {

  @Builder
  public ReservationFindCondition {
  }
}
