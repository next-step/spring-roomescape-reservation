package nextstep;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String name;

    public Reservation() {
    }

    public Reservation(LocalDate date, LocalTime time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
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
                ", name='" + name + '\'' +
                '}';
    }
}
