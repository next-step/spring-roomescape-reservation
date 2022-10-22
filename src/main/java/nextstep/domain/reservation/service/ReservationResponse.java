package nextstep.domain.reservation.service;

import nextstep.domain.reservation.model.Reservation;

public class ReservationResponse {
    private Long id;
    private String date;
    private String time;
    private String name;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.date = reservation.getDate().toString();
        this.time = reservation.getTime().toString();
        this.name = reservation.getName();
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
