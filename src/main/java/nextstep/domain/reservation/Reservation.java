package nextstep.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import nextstep.domain.reservation.exception.ReservationIllegalArgumentException;

@Getter
@EqualsAndHashCode
@ToString
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Reservation {

  Long id;
  LocalDate date;
  LocalTime time;
  Name name;

  @Builder(toBuilder = true)
  public Reservation(Long id, LocalDate date, LocalTime time, Name name) {
    this.id = id;
    this.date = date;
    this.time = adjustReservationTimeRule(time);
    this.name = name;
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

  public boolean hasSameDateTime(Reservation that) {
    return hasSameDateTime(that.date, that.time);
  }

  public boolean hasSameDateTime(LocalDate date, LocalTime time) {
    return this.date.equals(date) && this.time.equals(time);
  }
}
