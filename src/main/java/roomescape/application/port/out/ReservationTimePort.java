package roomescape.application.port.out;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimePort {

  ReservationTime saveReservationTime(ReservationTime reservationTime);

  List<ReservationTime> findReservationTimes();

  void deleteReservationTime(Long id);
}
