package nextstep.roomescape.core;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final Integer id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public Reservation(Integer id, LocalDate date, LocalTime time, String name) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public Integer getId() {
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
}
