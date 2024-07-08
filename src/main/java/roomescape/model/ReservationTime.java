package roomescape.model;

import java.time.LocalTime;

public class ReservationTime {
    private Long id;
    private LocalTime startAt;

    public ReservationTime() {}

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalTime startAt) {
        this.startAt = startAt;
    }
}
