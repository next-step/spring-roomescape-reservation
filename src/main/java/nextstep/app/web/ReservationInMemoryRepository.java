package nextstep.app.web;

import nextstep.core.Reservation;
import nextstep.core.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public List<Reservation> findAllByDate(LocalDate date) {
        Objects.requireNonNull(date);

        return RESERVATIONS.values().stream()
                .filter(reservation -> reservation.isSameDate(date))
                .toList();
    }
}
