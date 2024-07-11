package roomescape.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.application.ReservationRepository;
import roomescape.domain.reservationtime.application.ReservationTimeRepository;

@Component
public class DataCleanser {

    @Autowired
    ReservationTimeRepository reservationTimeRepository;

    @Autowired
    ReservationRepository reservationRepository;

    public void clean() {
        reservationRepository.deleteAllInBatch();
        reservationTimeRepository.deleteAllInBatch();
    }
}
