package nextstep.app.web.dto;

import nextstep.core.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCreateWebRequest {
    private LocalDate date;
    private LocalTime time;
    private String name;

    private ReservationCreateWebRequest() {
    }

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
