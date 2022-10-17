package nextstep.reservation.web.request;

public class CancelReservationRequest {

    private final Long scheduleId;
    private final String date;
    private final String time;

    public CancelReservationRequest(Long scheduleId, String date, String time) {
        this.scheduleId = scheduleId;
        this.date = date;
        this.time = time;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}


