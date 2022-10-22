package nextstep.domain.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder(toBuilder = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ScheduleEntity {

  Long id;
  Long themeId;
  LocalDate date;
  LocalTime time;
}
