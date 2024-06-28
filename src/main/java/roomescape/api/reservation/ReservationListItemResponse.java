package roomescape.api.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.Reservation;

record ReservationListItemResponse(long id, String name, LocalDate date, LocalTime time,
                                   String themeName) {

  static ReservationListItemResponse from(Reservation reservation) {
    return new ReservationListItemResponse(reservation.getId(), reservation.getName(),
        reservation.getDate(), reservation.getTime().getStartAt(),
        reservation.getTheme().name());
  }
}
