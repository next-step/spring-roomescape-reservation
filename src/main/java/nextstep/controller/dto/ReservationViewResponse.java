package nextstep.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.domain.reservation.Reservation.Name;

public class ReservationViewResponse {

  public record Reservation(
      LocalDate date,
      LocalTime time,
      Name name
  ) {

    public static Reservation of(nextstep.domain.reservation.Reservation domain) {
      return new Reservation(domain.getDate(), domain.getTime(), domain.getName());
    }
  }
}
