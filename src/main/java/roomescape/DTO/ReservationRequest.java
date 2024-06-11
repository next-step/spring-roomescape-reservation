package roomescape.DTO;

import roomescape.Entity.Reservation;

public class ReservationRequest {
    private String name;
    private String date;
    private Long timeId;

    public ReservationRequest() {

    }

    public ReservationRequest(String name, String date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}
