package roomescape.domain;

import roomescape.dto.reservation.create.ReservationCreateRequest;

import java.time.LocalTime;

public class ReservationTime {

    private Long id;

    private LocalTime startAt;

    public ReservationTime() {
    }

    public ReservationTime(String startAt) {
        this.startAt = LocalTime.parse(startAt);
    }

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
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
