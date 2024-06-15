package roomescape.DTO;

import roomescape.Entity.ReservationTime;

import java.util.List;

public class ReservationTimeResponse {
    private final Long id;
    private final String startAt;

    public ReservationTimeResponse(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTimeResponse(ReservationTime reservationTime) {
        this.id = reservationTime.getId();
        this.startAt = reservationTime.getStartAt();
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return this.startAt;
    }

    public static List<ReservationTimeResponse> toResponses(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream().map(ReservationTimeResponse::new).toList();
    }
}
