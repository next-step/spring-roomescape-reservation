package roomescape.application.port.out;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationPort {

  List<Reservation> findReservations();

  Reservation saveReservation(Reservation reservation);

  void deleteReservation(Long id);
}
