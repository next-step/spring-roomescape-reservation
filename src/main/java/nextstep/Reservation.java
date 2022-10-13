package nextstep;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

    private Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public Reservation(Long id, LocalDate date, LocalTime time, String name) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public static Reservation of(LocalDate date, LocalTime time, String name) {
        return new Reservation(0L, date, time, name);
    }

    public boolean isSameDateTime(LocalDate date, LocalTime time) {
        return Objects.equals(this.date, date) && Objects.equals(this.time, time);
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
