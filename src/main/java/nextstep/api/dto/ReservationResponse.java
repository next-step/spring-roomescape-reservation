package nextstep.api.dto;

import nextstep.domain.Reservation;

public class ReservationResponse {
    public Long id;
    public String date;
    public String time;
    public String name;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.date = reservation.getDate().toString();
        this.time = reservation.getTime().toString();
        this.name = reservation.getName();
    }
}
