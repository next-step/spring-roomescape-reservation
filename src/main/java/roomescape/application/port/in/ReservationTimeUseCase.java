package roomescape.application.port.in;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeUseCase {

  ReservationTime registerReservationTime(ReservationTime reservationTime);

  List<ReservationTime> retrieveReservationTimes();

  void deleteReservationTime(Long id);
}
