package nextstep.domain;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class Reservations {
    private Long tmpId;
    private final List<Reservation> values;

    public Reservations() {
        this.tmpId = 1L;
        this.values = new ArrayList<>();
    }

    public Reservation save(LocalDate date, LocalTime time, String name) {
        Reservation reservation = new Reservation(tmpId++, date, time, name);
        values.add(reservation);
        return reservation;
    }

    public List<Reservation> findAllByDate(LocalDate date) {
        return values.stream()
                .filter(it -> it.equalsDate(date))
                .collect(Collectors.toList());
    }

    public void deleteByDateAndTime(LocalDate date, LocalTime time) {
        values.removeIf(it -> it.equalsDateAndTime(date, time));
    }

    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return values.stream()
                .anyMatch(it -> it.equalsDateAndTime(date, time));
    }
}
