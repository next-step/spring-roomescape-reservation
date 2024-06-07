package roomescape.reservation.web.dto;

import roomescape.reservation.domain.Reservation;

public class ReservationRequest {

    private final String name;
    private final String date;
    private final String time;

    public ReservationRequest(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation toEntity() {
        return new Reservation(null, this.name, this.date, this.time);
    }
}
