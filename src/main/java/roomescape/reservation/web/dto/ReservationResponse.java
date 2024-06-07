package roomescape.reservation.web.dto;

import roomescape.reservation.domain.Reservation;

public class ReservationResponse {

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
