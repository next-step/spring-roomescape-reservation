package roomescape.domain.reservation;

import java.time.LocalDate;
import roomescape.domain.theme.Theme;
import roomescape.domain.time.ReservationTime;

public record CreateReservation(String name, LocalDate date, long timeId, long themeId) {
  public Reservation toReservation(long id) {
    return new Reservation(id, name, date, new ReservationTime(timeId), new Theme(themeId));
  }
}
