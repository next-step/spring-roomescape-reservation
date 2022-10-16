package nextstep.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public void remove(LocalDate targetDate, LocalTime targetTime) {
        reservations.stream()
            .filter(it -> Objects.equals(it.getDate(), targetDate) && Objects.equals(it.getTime(), targetTime))
            .findFirst()
            .ifPresent(reservations::remove);
    }

    public List<Reservation> findBy(LocalDate targetDate) {
        return reservations.stream()
            .filter(it -> Objects.equals(it.getDate(), targetDate))
            .toList();
    }
}
