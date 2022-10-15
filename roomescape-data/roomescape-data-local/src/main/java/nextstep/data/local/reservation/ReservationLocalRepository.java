package nextstep.data.local.reservation;

import nextstep.domain.reservation.domain.model.Reservation;
import nextstep.domain.reservation.domain.model.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationLocalRepository implements ReservationRepository {
    private static final AtomicLong RESERVATION_ID = new AtomicLong(1);
    private static final Map<Long, Reservation> RESERVATIONS = new ConcurrentHashMap<>();

    @Override
    public Reservation save(Reservation reservation) {
        return Optional.ofNullable(reservation.id())
                .map(it -> {
                    RESERVATIONS.put(reservation.id(), reservation);
                    return RESERVATIONS.get(it);
                })
                .orElseGet(() -> {
                    long id = RESERVATION_ID.getAndIncrement();
                    RESERVATIONS.put(id, reservation.withId(id));
                    return RESERVATIONS.get(id);
                });
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.of(RESERVATIONS.get(id));
    }
}
