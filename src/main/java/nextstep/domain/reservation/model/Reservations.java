package nextstep.domain.reservation.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Reservations {
    private final AtomicLong seq = new AtomicLong(1);
    private static final List<Reservation> RESERVATIONS = new ArrayList<>();

    public Reservation create(LocalDate date, LocalTime time, String name) {
        Reservation reservation = new Reservation(seq.getAndIncrement(), date, time, name);
        RESERVATIONS.add(reservation);

        return reservation;
    }

    public void cancelByDateAndTime(LocalDate date, LocalTime time) {
        RESERVATIONS.stream()
                .filter(it -> Objects.equals(it.getDate(), date) && Objects.equals(it.getTime(), time))
                .findFirst()
                .ifPresent(RESERVATIONS::remove);
    }

    public List<Reservation> findAllByDate(LocalDate date) {
        return RESERVATIONS.stream()
                .filter(it -> it.getDate().isEqual(date))
                .collect(Collectors.toList());
    }
}
