package nextstep.domain.reservation.model;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class Reservation {
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public Reservation(LocalDate date, LocalTime time, String name) {
        this(null, date, time, name);
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public Boolean sameDateTime(LocalDate date, LocalTime time) {
        return this.date.isEqual(date) && this.time.equals(time);
    }

    public Boolean sameDate(LocalDate date) {
        return this.date.isEqual(date);
    }
}
