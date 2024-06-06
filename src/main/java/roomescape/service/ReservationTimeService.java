package roomescape.service;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeService {

    ReservationTime create(ReservationTime reservationTime);

    List<ReservationTime> read();

    void delete(Long id);
}
