package roomescape.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.application.port.in.ReservationUseCase;
import roomescape.application.port.out.ReservationPort;
import roomescape.domain.Reservation;

@Transactional
@Service
public class ReservationService implements ReservationUseCase {

  private final ReservationPort reservationPort;

  public ReservationService(ReservationPort reservationPort) {
    this.reservationPort = reservationPort;
  }

  @Transactional(readOnly = true)
  @Override
  public List<Reservation> retrieveReservations() {
    return reservationPort.findReservations();
  }

  @Override
  public Reservation registerReservation(Reservation reservation) {
    return reservationPort.saveReservations(reservation);
  }

  @Override
  public void deleteReservation(Long id) {
    reservationPort.deleteReservation(id);
  }
}
