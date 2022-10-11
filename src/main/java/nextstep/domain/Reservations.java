package nextstep.domain;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {

    private final AtomicLong id;
    private final List<Reservation> values;

    public Reservations() {
        this.id = new AtomicLong(1);
        this.values = new ArrayList<>();
    }

    public Reservation add(LocalDate date, LocalTime time, String name) {
        if (existsReservationByDateTime(date, time)) {
            throw new IllegalArgumentException("날짜와 시간이 똑같은 예약이 이미 존재합니다.");
        }

        Reservation reservation = new Reservation(id.getAndIncrement(), date, time, name);
        values.add(reservation);
        return reservation;
    }

    private boolean existsReservationByDateTime(LocalDate date, LocalTime time) {
        return values.stream()
            .anyMatch(it -> it.equalsDateTime(date, time));
    }

    public List<Reservation> findAllByDate(LocalDate date) {
        return values.stream()
            .filter(it -> Objects.equals(it.getDate(), date))
            .collect(toList());
    }

    public void removeByDateTime(LocalDate date, LocalTime time) {
        values.removeIf(it -> it.equalsDateTime(date, time));
    }
}
