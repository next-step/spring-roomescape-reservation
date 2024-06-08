package roomescape.application.port.in;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationUseCase {

  List<Reservation> retrieveReservations();

  Reservation registerReservation(Reservation reservation);

  void deleteReservation(Long id);
}
