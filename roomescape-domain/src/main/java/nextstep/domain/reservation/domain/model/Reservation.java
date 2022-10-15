package nextstep.domain.reservation.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Long id,
                          LocalDate date,
                          LocalTime time,
                          String name) {
    public Reservation withId(Long id) {
        return new Reservation(id, date, time, name);
    }
}
