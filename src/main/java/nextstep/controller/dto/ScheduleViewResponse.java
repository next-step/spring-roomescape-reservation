package nextstep.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleViewResponse {

  public record Schedule(
      Long id,
      Long themeId,
      LocalDate date,
      LocalTime time
  ) {

    public static Schedule of(nextstep.domain.schedule.Schedule domain) {
      return new Schedule(domain.getId(), domain.getThemeId(), domain.getDate(), domain.getTime());
    }
  }
}
