package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;

    private ReservationTime time;

    public Reservation() {
    }

    public Reservation(Long id, String name, String date, Long timeId, String startAt) {
        this.id = id;
        this.name = name;
        this.date = LocalDate.parse(date);
        this.time = new ReservationTime(timeId, startAt);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
