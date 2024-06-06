package roomescape.apply.reservation.ui.dto;

import roomescape.apply.reservation.domain.Reservation;
import roomescape.apply.time.domain.ReservationTime;
import roomescape.apply.time.ui.dto.ReservationTimeResponse;

public record ReservationResponse(
        long id,
        String name,
        String date,
        ReservationTimeResponse time
) {
    public static ReservationResponse from(Reservation reservation, ReservationTime reservationTime) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                ReservationTimeResponse.from(reservationTime));
    }
}
