package roomescape.apply.time.ui.dto;

import roomescape.apply.time.domain.ReservationTime;

public record ReservationTimeResponse(
        long id,
        String startAt
) {
    public static ReservationTimeResponse from(ReservationTime saved) {
        return new ReservationTimeResponse(saved.getId(), saved.getStartAt());
    }
}
