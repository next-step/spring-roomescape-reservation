package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public class ReservationTime {
    private final long id;
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime startAt;

    public ReservationTime(long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
