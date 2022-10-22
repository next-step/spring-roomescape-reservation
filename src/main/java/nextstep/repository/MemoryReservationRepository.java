package nextstep.repository;

import nextstep.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryReservationRepository implements ReservationRepository {

    private final Map<LocalDate, Map<LocalTime, Reservation>> dataBase = new ConcurrentHashMap<>();

    // 동시에 여러개가 들어올 수 있다.
    // 스레드 세이프하게 처리가 필요하다.(동시성 이슈)
    private final AtomicLong id = new AtomicLong(1L);

    @Override
    public List<Reservation> findByDate(String date) {
        return null;
    }

    @Override
    public long save(LocalDate date, LocalTime time, String name) {
        Map<LocalTime, Reservation> localTimeReservationMap = dataBase.computeIfAbsent(date, k -> new ConcurrentHashMap<>());
        // checkedException RuntimeException 차이
        if (localTimeReservationMap.containsKey(time)) {
            throw new IllegalArgumentException("중복된 시간에 등록이 되어 있습니다.");
        }
        Reservation reservation = localTimeReservationMap.computeIfAbsent(time, k -> new Reservation(id.getAndIncrement(), date, time, name));
        return reservation.getId();
    }

    //    @Override
//    public List<Reservation> findByDate(String date) {
//        return store.values().stream()
//                .filter(it -> it.getDate().isEqual(LocalDate.parse(date)))
//                .collect(Collectors.toList());
//    }
//
//    public void deleteByDateAndTime(String date, String time) {
//        for (long key : store.keySet()) {
//            Reservation res = store.get(key);
//            if (Objects.equals(res.getDate(), LocalDate.parse(date)) && Objects.equals(res.getTime(), LocalDate.parse(time))) {
//                store.remove(key);
//            }
//        }
//    }
}
