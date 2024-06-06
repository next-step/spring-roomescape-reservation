package roomescape.time.application;

import java.time.LocalTime;

import roomescape.time.domain.ReservationTime;

public record ReservationTimeResponse(Long reservationTimeId, LocalTime startAt) {

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }
}
