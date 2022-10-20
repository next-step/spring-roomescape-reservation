package nextstep.app.web.dto;

import nextstep.core.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationWebResponse {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Long id;

    private ReservationWebResponse(LocalDate date, LocalTime time, String name, Long id) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.id = id;
    }

    public static ReservationWebResponse from(Reservation reservation) {
        return new ReservationWebResponse(reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getId());
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

    public Long getId() {
        return id;
    }
}
