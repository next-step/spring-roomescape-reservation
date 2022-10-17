package nextstep.reservation.web.request;

public class ListReservationRequest {

    private final Long scheduleId;
    private final String date;

    public ListReservationRequest(Long scheduleId, String date) {
        this.scheduleId = scheduleId;
        this.date = date;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public String getDate() {
        return date;
    }
}
