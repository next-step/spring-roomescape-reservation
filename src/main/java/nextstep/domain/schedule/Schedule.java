package nextstep.domain.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@EqualsAndHashCode
@ToString
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Schedule {

  Long id;
  Long themeId;
  LocalDate date;
  LocalTime time;

  @Builder(toBuilder = true)
  public Schedule(Long id, Long themeId, LocalDate date, LocalTime time) {
    this.id = id;
    this.themeId = themeId;
    this.date = date;
    this.time = adjustReservationTimeRule(time);
  }

  public static LocalTime adjustReservationTimeRule(LocalTime time) {
    if (time == null) {
      return null;
    }
    return time.truncatedTo(ChronoUnit.SECONDS);
  }

}
