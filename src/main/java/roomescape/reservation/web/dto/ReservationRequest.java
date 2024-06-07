package roomescape.reservation.web.dto;

public class ReservationRequest {

    private final String name;
    private final String date;
    private final Long timeId;

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
