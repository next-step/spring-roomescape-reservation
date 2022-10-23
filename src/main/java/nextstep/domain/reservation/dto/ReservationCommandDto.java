package nextstep.domain.reservation.dto;

import static nextstep.domain.reservation.Reservation.adjustReservationTimeRule;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

public class ReservationCommandDto {

  public record Create(
      Long scheduleId,
      String name
  ) {
  }

  public record Delete(
      LocalDate date,
      LocalTime time
  ) {

    @Jacksonized
    @Builder(toBuilder = true)
    public Delete {
      time = adjustReservationTimeRule(time);
    }
  }
}
