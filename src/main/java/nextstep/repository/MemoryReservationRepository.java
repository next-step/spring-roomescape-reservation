package nextstep.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import nextstep.domain.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryReservationRepository implements ReservationRepository {

    private final Map<LocalDate, Map<LocalTime, Reservation>> dataBase = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(1L);

    @Override
    public long save(LocalDate date, LocalTime time, String name) {
        Map<LocalTime, Reservation> localTimeReservationMap = dataBase
            .computeIfAbsent(date, k -> new ConcurrentHashMap<>());
        Reservation reservation = localTimeReservationMap
            .computeIfAbsent(time, k -> new Reservation(id.getAndIncrement(), date, time, name));
        return reservation.getId();
    }

    @Override
    public List<Reservation> findReservationsByDate(LocalDate date) {
        Map<LocalTime, Reservation> localTimeReservationMap = dataBase
            .computeIfAbsent(date, k -> new ConcurrentHashMap<>());
        return new ArrayList<>(localTimeReservationMap.values());
    }

    @Override
    public void deleteByLocalDateAndLocalTime(LocalDate date, LocalTime time) {
        Map<LocalTime, Reservation> localTimeReservationMap = dataBase
            .computeIfAbsent(date, k -> new ConcurrentHashMap<>());
        localTimeReservationMap.remove(time);
    }

    @Override
    public boolean existReservationByDateAndTime(LocalDate date, LocalTime time) {
        Map<LocalTime, Reservation> localTimeReservationMap = dataBase
            .computeIfAbsent(date, k -> new ConcurrentHashMap<>());
        return localTimeReservationMap.containsKey(time);
    }
}
