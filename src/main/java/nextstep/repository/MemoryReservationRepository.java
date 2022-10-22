package nextstep.repository;

import nextstep.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemoryReservationRepository implements ReservationRepository{
    private static Map<Long, Reservation> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Reservation save(Reservation reservation) {
        reservation.setId(++sequence);
        store.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public List<Reservation> findByDate(String date) {
        return store.values().stream()
                .filter(it -> it.getDate().isEqual(LocalDate.parse(date)))
                .collect(Collectors.toList());
    }

    public void deleteByDateAndTime(String date, String time) {
        for (long key : store.keySet()) {
            Reservation res = store.get(key);
            if (Objects.equals(res.getDate(), LocalDate.parse(date)) && Objects.equals(res.getTime(), LocalDate.parse(time))) {
                store.remove(key);
            }
        }
    }
}
