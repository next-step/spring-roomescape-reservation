package nextstep.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public Reservation(LocalDate date, LocalTime time, String name) {
        this(null, date, time, name);
    }

    public Reservation(Long id, Reservation reservation) {
        this(id, reservation.getDate(), reservation.getTime(), reservation.getName());
    }
    
    public Reservation(Long id, LocalDate date, LocalTime time, String name) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public boolean equalsDateTime(LocalDate date, LocalTime time) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
