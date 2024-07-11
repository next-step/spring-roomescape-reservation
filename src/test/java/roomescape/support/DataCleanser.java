package roomescape.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.infrastructure.ReservationJdbcRepository;
import roomescape.domain.reservationtime.infrastructure.ReservationTimeJdbcRepository;

@Component
public class DataCleanser {

    @Autowired
    ReservationTimeJdbcRepository reservationTimeRepository;

    @Autowired
    ReservationJdbcRepository reservationRepository;

    public void clean() {
        reservationRepository.deleteAllInBatch();
        reservationTimeRepository.deleteAllInBatch();
    }
}
