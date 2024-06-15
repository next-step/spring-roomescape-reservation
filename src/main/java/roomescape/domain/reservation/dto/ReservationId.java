package roomescape.domain.reservation.dto;

import roomescape.domain.reservation.model.Reservation;

public record ReservationId(
        Long value
) {

    public static ReservationId from(final Reservation reservation) {
        return new ReservationId(reservation.getId());
    }

}
