package roomescape.dto.time.create;

import java.time.LocalTime;

public class ReservationTimeCreateRequest {

    private LocalTime startAt;

    public ReservationTimeCreateRequest() {
    }

    public ReservationTimeCreateRequest(String startAt) {
        this.startAt = LocalTime.parse(startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
