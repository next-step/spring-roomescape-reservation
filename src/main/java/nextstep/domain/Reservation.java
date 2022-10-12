package nextstep.domain;

import java.time.LocalDate;
import java.time.LocalTime;

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

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Boolean equalsDate(LocalDate date) {
        return this.date.equals(date);
    }

    public Boolean equalsDateAndTime(LocalDate date, LocalTime time) {
        return this.date.equals(date) && this.time.equals(time);
    }
}
