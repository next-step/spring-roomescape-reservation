package nextstep;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Reservation {

    private static final AtomicLong INCREMENT_COUNT = new AtomicLong();
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public Reservation(LocalDate date, LocalTime time, String name) {
        this.id = INCREMENT_COUNT.incrementAndGet();
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public boolean isSameDateTime(LocalDate date, LocalTime time) {
        return Objects.equals(this.date, date) && Objects.equals(this.time, time);
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
}
