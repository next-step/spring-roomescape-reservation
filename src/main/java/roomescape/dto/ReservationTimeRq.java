package roomescape.dto;

import java.time.LocalTime;

public class ReservationTimeRq {
    private LocalTime startAt;

    public ReservationTimeRq() {}

    public ReservationTimeRq(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalTime startAt) {
        this.startAt = startAt;
    }
}
