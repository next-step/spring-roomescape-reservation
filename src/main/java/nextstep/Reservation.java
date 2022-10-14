package nextstep;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

    private LocalDate date;
    private LocalTime time;
    private String name;

    private Reservation() {
    }

    public Reservation(String date, String time, String name) {
        this(LocalDate.parse(date), LocalTime.parse(time + ":00"), name);
    }

    public Reservation(LocalDate date, LocalTime time, String name) {
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
