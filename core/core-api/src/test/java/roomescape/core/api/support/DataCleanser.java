package roomescape.core.api.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.db.core.reservation.ReservationJdbcRepository;
import roomescape.db.core.reservatiotime.ReservationTimeJdbcRepository;
import roomescape.db.core.theme.ThemeJdbcRepository;

@Component
public class DataCleanser {

    @Autowired
    ReservationTimeJdbcRepository reservationTimeRepository;

    @Autowired
    ReservationJdbcRepository reservationRepository;

    @Autowired
    ThemeJdbcRepository themeRepository;

    public void clean() {
        reservationRepository.deleteAllInBatch();
        reservationTimeRepository.deleteAllInBatch();
        themeRepository.deleteAllInBatch();
    }
}
