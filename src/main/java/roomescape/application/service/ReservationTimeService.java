package roomescape.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.application.port.in.ReservationTimeUseCase;
import roomescape.application.port.out.ReservationTimePort;
import roomescape.domain.ReservationTime;

@Transactional
@Service
public class ReservationTimeService implements ReservationTimeUseCase {

  private final ReservationTimePort reservationTimePort;

  public ReservationTimeService(ReservationTimePort reservationTimePort) {
    this.reservationTimePort = reservationTimePort;
  }

  @Override
  public ReservationTime registerReservationTime(ReservationTime reservationTime) {
    return reservationTimePort.saveReservationTime(reservationTime);
  }

  @Transactional(readOnly = true)
  @Override
  public List<ReservationTime> retrieveReservationTimes() {
    return reservationTimePort.findReservationTimes();
  }

  @Override
  public void deleteReservationTime(Long id) {
    reservationTimePort.deleteReservationTime(id);
  }
}
