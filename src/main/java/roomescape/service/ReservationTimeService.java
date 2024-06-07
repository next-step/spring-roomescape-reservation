package roomescape.service;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeService {

    ReservationTime createReservationTime(ReservationTime reservationTime);

    List<ReservationTime> findAllReservationTimes();

    void deleteReservationTime(Long id);
}
