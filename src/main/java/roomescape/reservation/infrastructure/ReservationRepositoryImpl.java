package roomescape.reservation.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.repository.ReservationRepository;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private static Map<Long, Reservation> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Reservation save(Reservation reservation) {
        reservation.setId(++sequence);
        store.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(Long reservationId) {
        store.remove(reservationId);
    }

    @Override
    public boolean existsById(Long reservationId) {
        return store.containsKey(reservationId);
    }
}
