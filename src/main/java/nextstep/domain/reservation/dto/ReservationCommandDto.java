package nextstep.domain.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

public class ReservationCommandDto {

  public record Create(
      LocalDate date,
      LocalTime time,
      String name
  ) {

    @Builder(toBuilder = true)
    public Create {
    }
  }

  public record Delete(
      LocalDate date,
      LocalTime time
  ) {

    @Builder(toBuilder = true)
    public Delete {
    }
  }
}
