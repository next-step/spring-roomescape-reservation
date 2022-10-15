package nextstep.app.web;

import nextstep.core.Reservation;
import nextstep.core.ReservationRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {
    @Override
    public Reservation save(Reservation reservation) {
        return null;
    }
}
