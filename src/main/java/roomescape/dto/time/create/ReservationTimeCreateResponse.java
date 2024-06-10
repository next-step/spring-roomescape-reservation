package roomescape.dto.time.create;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;

public class ReservationTimeCreateResponse {

    private Long id;
    private LocalTime startAt;

    public static ReservationTimeCreateResponse toDto(ReservationTime entity) {
        return new ReservationTimeCreateResponse(entity.getId(), entity.getStartAt());
    }

    public ReservationTimeCreateResponse() {
    }

    public ReservationTimeCreateResponse(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
