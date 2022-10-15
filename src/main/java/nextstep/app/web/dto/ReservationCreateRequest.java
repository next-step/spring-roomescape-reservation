package nextstep.app.web.dto;

import nextstep.core.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCreateRequest {
    private LocalDate date;
    private LocalTime time;
    private String name;

    public Reservation to() {
        return new Reservation(date, time, name);
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
