package roomescape.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.application.port.in.ReservationUseCase;
import roomescape.application.port.out.ReservationPort;
import roomescape.application.port.out.ReservationTimePort;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.ReservationTimeConflictException;

@Transactional
@Service
public class ReservationService implements ReservationUseCase {

  private final ReservationPort reservationPort;
  private final ReservationTimePort reservationTimePort;

  public ReservationService(ReservationPort reservationPort, ReservationTimePort reservationTimePort) {
    this.reservationPort = reservationPort;
    this.reservationTimePort = reservationTimePort;
  }

  @Transactional(readOnly = true)
  @Override
  public List<Reservation> retrieveReservations() {
    return reservationPort.findReservations();
  }

  @Override
  public Reservation registerReservation(Reservation reservation) {
    ReservationTime reservationTime = reservationTimePort.findReservationTimeByStartAt(reservation.getTime()
                                                                                                  .getStartAt())
                                                         .orElseThrow(NotFoundReservationException::new);

    if (reservationPort.findReservationByReservationTime(reservationTime)
                       .isPresent()) {
      throw new ReservationTimeConflictException();
    }

    return reservationPort.saveReservation(reservation, reservationTime);
  }

  @Override
  public void deleteReservation(Long id) {
    if (reservationPort.countReservationById(id) == 0) {
      throw new NotFoundReservationException();
    }

    reservationPort.deleteReservation(id);
  }
}
