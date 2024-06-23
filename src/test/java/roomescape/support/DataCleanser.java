package roomescape.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.reservationtime.repository.ReservationTimeRepository;

@Component
public class DataCleanser {

    @Autowired
    ReservationTimeRepository reservationTimeRepository;

    @Autowired
    ReservationRepository reservationRepository;

    public void clean() {
        reservationTimeRepository.deleteAllInBatch();
        reservationRepository.deleteAllInBatch();
    }
}
