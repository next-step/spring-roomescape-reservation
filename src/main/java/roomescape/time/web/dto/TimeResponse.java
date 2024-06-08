package roomescape.time.web.dto;

import roomescape.time.domain.Time;

public class TimeResponse {

    private final Long id;
    private final String startAt;

    public TimeResponse(Time time) {
        this.id = time.getId();
        this.startAt = time.getStartAt();
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
