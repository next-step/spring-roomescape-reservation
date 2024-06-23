package roomescape.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.repository.ReservationRepository;

@Component
public class DataCleanser {

    @Autowired
    ReservationRepository reservationRepository;

    public void clean() {
        reservationRepository.deleteAllInBatch();
    }
}
