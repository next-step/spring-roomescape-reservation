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

    public boolean isDateEqual(LocalDate date) {
        return this.date.isEqual(date);
    }

    public boolean isTimeEqual(LocalTime time) {
        return this.time.equals(time);
    }
}
