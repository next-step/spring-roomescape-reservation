package roomescape.apply.reservationtime.ui.dto;

import roomescape.apply.reservationtime.domain.ReservationTime;

public record ReservationTimeResponse(
        long id,
        String startAt
) {
    public static ReservationTimeResponse from(ReservationTime saved) {
        return new ReservationTimeResponse(saved.getId(), saved.getStartAt());
    }
}
