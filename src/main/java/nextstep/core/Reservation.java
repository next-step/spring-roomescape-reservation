package nextstep.core;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private Long id;

    public Reservation(LocalDate date, LocalTime time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public boolean isSameDate(LocalDate date) {
        return this.date.equals(date);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
