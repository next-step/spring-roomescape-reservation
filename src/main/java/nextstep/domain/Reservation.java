package nextstep.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;

    private Reservation() {
    }

    public Reservation(String date, String time, String name) {
        this(null, LocalDate.parse(date), LocalTime.parse(time), name);
    }

    public Reservation(Long id, String date, String time, String name) {
        this(id, LocalDate.parse(date), LocalTime.parse(time), name);
    }

    public Reservation(Long id, LocalDate date, LocalTime time, String name) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public boolean isSame(String date, String time) {
        return Objects.equals(this.getDate(), LocalDate.parse(date)) &&
            Objects.equals(this.getTime(), LocalTime.parse(time));
    }

    public boolean isSame(String date) {
        return Objects.equals(this.getDate(), LocalDate.parse(date));
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
    public String toString() {
        return "Reservation{" +
            "date=" + date +
            ", time=" + time +
            '}';
    }
}
