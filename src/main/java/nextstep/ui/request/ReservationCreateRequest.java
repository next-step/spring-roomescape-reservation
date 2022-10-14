package nextstep.ui.request;

public class ReservationCreateRequest {

    private Long scheduleId;
    private String name;

    protected ReservationCreateRequest() {
    }

    public ReservationCreateRequest(Long scheduleId, String name) {
        this.scheduleId = scheduleId;
        this.name = name;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public String getName() {
        return name;
    }
}
