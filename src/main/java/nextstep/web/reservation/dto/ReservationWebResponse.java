package nextstep.web.reservation.dto;

import nextstep.domain.reservation.service.ReservationResponse;

public class ReservationWebResponse {
    public Long id;
    public String date;
    public String time;
    public String name;

    public ReservationWebResponse(ReservationResponse response) {
        this.id = response.getId();
        this.date = response.getDate();
        this.time = response.getTime();
        this.name = response.getName();
    }
}
