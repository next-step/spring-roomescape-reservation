package roomescape.reservationtime.ui.dto;

import roomescape.reservationtime.domain.entity.ReservationTime;

import java.util.List;

public class ReservationTimeResponse {
    private final Long id;
    private final String startAt;

    public ReservationTimeResponse(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return this.startAt;
    }

    public static List<ReservationTimeResponse> fromReservationTimes(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream().map(ReservationTimeResponse::from).toList();
    }
}
