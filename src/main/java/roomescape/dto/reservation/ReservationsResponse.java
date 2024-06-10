package roomescape.dto.reservation;

import roomescape.domain.Reservation;
import roomescape.dto.time.ReservationTimeResponse;

import java.time.LocalDate;

public class ReservationsResponse {

    private Long id;

    private String name;
    private LocalDate date;

    private ReservationTimeResponse time;


    public ReservationsResponse() {
    }

    public ReservationsResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = new ReservationTimeResponse(reservation.getTime());
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

    public ReservationTimeResponse getTime() {
        return time;
    }
}
