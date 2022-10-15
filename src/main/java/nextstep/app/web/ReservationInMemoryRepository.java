package nextstep.app.web;

import nextstep.core.Reservation;
import nextstep.core.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
public class ReservationInMemoryRepository implements ReservationRepository {
    private static final Map<Long, Reservation> RESERVATIONS = new HashMap<>();
    private long incrementor = 1;

    @Override
    public Reservation save(Reservation reservation) {
        Objects.requireNonNull(reservation);

        reservation.setId(incrementor++);
        RESERVATIONS.put(reservation.getId(), reservation);
        return reservation;
    }
}
