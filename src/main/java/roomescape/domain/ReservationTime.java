package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {

    private Long id;

    private LocalTime startAt;

    public ReservationTime() {
    }

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = LocalTime.parse(startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

}
