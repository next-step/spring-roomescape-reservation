package nextstep.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import nextstep.domain.reservation.exception.ReservationIllegalArgumentException;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Reservation {

  LocalDate date;
  LocalTime time;
  Name name;

  public LocalDate getDate() {
    return date;
  }

  public LocalTime getTime() {
    return time;
  }

  public Name getName() {
    return name;
  }

  public record Name(String name) {

    public static Name of(String value) {
      if (value == null || value.isEmpty()) {
        throw new ReservationIllegalArgumentException("예약의 이름은 공란일 수 없습니다.");
      }

      return new Name(value);
    }
  }

  public boolean hasSameDateTime(Reservation that){
    return hasSameDateTime(that.date, that.time);
  }

  public boolean hasSameDateTime(LocalDate date, LocalTime time){
    return this.date.equals(date) && this.time.equals(time);
  }
}
