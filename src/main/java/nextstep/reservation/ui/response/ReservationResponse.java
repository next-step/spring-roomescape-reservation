package nextstep.reservation.ui.response;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.reservation.domain.Reservation;

public class ReservationResponse {

    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public ReservationResponse(Long id, LocalDate date, LocalTime time, String name) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public static List<ReservationResponse> of(List<Reservation> reservations) {
        return reservations.stream()
            .map(ReservationResponse::from)
            .collect(toList());
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getDate(),
            reservation.getTime(),
            reservation.getName()
        );
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
