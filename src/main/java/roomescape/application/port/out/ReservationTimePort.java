package roomescape.application.port.out;

import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface ReservationTimePort {

  ReservationTime saveReservationTime(ReservationTime reservationTime);

  List<ReservationTime> findReservationTimes();

  void deleteReservationTime(Long id);

  Optional<ReservationTime> findReservationTimeById(Long id);

  Optional<ReservationTime> findReservationTimeByStartAt(String startAt);
}
