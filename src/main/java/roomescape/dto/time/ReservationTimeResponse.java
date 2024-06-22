package roomescape.dto.time;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;

public class ReservationTimeResponse {

    private Long id;

    private LocalTime startAt;

    public static ReservationTimeResponse toDto(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime);
    }

    public ReservationTimeResponse() {
    }

    public ReservationTimeResponse(ReservationTime reservationTime) {
        this.id = reservationTime.getId();
        this.startAt = reservationTime.getStartAt();
    }


    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
