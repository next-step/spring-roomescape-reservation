package nextstep.domain.reservation;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import nextstep.domain.schedule.Schedule;
import nextstep.exception.ReservationIllegalArgumentException;

@Getter
@EqualsAndHashCode
@ToString
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Reservation {

  Long id;
  Name name;
  Schedule schedule;

  @Builder(toBuilder = true)
  private Reservation(Long id, Name name, Schedule schedule) {
    this.id = id;
    this.name = name;
    this.schedule = schedule;
  }

  public static LocalTime adjustReservationTimeRule(LocalTime time) {
    if (time == null) {
      return null;
    }
    return time.truncatedTo(ChronoUnit.SECONDS);
  }

  public record Name(String value) {

    public static Name of(String value) {
      if (value == null || value.isEmpty()) {
        throw new ReservationIllegalArgumentException("예약의 이름은 공란일 수 없습니다.");
      }

      return new Name(value);
    }
  }

  public boolean hasSameSchedule(Reservation that) {
    return this.schedule.equals(that.schedule);
  }
}
