package nextstep.dto;

public class ReservationCreateRequest {
    private final Long scheduleId;
    private final String name;

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
