package nextstep.reservation.controller.response;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.reservation.Reservation;

public class ReservationFindResponse {

    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public ReservationFindResponse(Long id, LocalDate date, LocalTime time, String name) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public static ReservationFindResponse from(Reservation reservation) {
        return new ReservationFindResponse(reservation.getId(), reservation.getDate(), reservation.getTime(), reservation.getName());
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
