package roomescape.time.presentation.dto;

public class TimeResponse {

    private final Long id;
    private final String startAt;

    public TimeResponse(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
