package nextstep;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public class Reservation {

    private static AtomicLong incrementCount = new AtomicLong();
    private Long id;

    private LocalDate date;
    private LocalTime time;
    private String name;

    public Reservation(LocalDate date, LocalTime time, String name) {
        this.id = incrementCount.incrementAndGet();
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
}
