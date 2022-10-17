package nextstep.domain.schedule.dto;

import static nextstep.domain.schedule.Schedule.adjustReservationTimeRule;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

public class ScheduleCommandDto {

  public record Create(
      Long themeId,
      LocalDate date,
      LocalTime time
  ) {

    @Jacksonized
    @Builder(toBuilder = true)
    public Create {
      adjustReservationTimeRule(time);
    }
  }

  public record Delete(
      Long id
  ) {

    @Jacksonized
    @Builder(toBuilder = true)
    public Delete {
    }
  }
}
