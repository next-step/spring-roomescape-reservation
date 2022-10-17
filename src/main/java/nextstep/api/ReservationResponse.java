package nextstep.api;

import nextstep.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationResponse {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    private ReservationResponse(LocalDate date, LocalTime time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }

    private static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getDate(), reservation.getTime(), reservation.getName());
    }

    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream()
            .map(ReservationResponse::from)
            .toList();
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
