package roomescape.dto;

import java.time.LocalTime;

public class ReservationTimeRs {
    private Long id;
    private LocalTime startAt;

    public ReservationTimeRs() {}

    public ReservationTimeRs(Long id, LocalTime startAt) {
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
