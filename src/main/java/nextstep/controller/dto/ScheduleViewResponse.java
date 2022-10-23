package nextstep.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleViewResponse {

  public record Schedule(
      Long id,
      ThemeViewResponse.Theme theme,
      LocalDate date,
      LocalTime time
  ) {

    public static Schedule of(nextstep.domain.schedule.Schedule domain) {
      return new Schedule(domain.getId(), ThemeViewResponse.Theme.of(domain.getTheme()), domain.getDate(), domain.getTime());
    }
  }

  public record SimpleSchedule(
      Long id
  ) {

    public static SimpleSchedule of(nextstep.domain.schedule.Schedule domain) {
      return new SimpleSchedule(domain.getId());
    }
  }
}
