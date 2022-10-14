package nextstep.core;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Reservations {

    private final Map<Integer, Reservation> reservations = new HashMap<>();
    private final AtomicInteger atomicInteger = new AtomicInteger();

    public List<Reservation> findAllByDate(LocalDate date) {
        return reservations.values().stream()
            .filter(it -> it.getDate().isEqual(date))
            .collect(Collectors.toList());
    }

    public void cancelByDateTime(LocalDate date, LocalTime time) {
        if (!existsByDateAndTime(date, time)) {
            throw new IllegalArgumentException("해당 날짜, 시간에 이미 예약 정보가 존재하지 않습니다.");
        }
        reservations.values().removeIf(it -> Objects.equals(it.getDate(), date) && Objects.equals(it.getTime(), time));
    }

    public int register(LocalDate date, LocalTime time, String name) {
        if (existsByDateAndTime(date, time)) {
            throw new IllegalArgumentException("해당 날짜, 시간에 예약 정보가 존재합니다.");
        }
        final int id = atomicInteger.incrementAndGet();
        reservations.put(id, new Reservation(date, time, name));
        return id;
    }

    private boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.values().stream()
            .anyMatch(it -> Objects.equals(it.getDate(), date) && Objects.equals(it.getTime(), time));
    }
}
