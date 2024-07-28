package roomescape.core.api.reservation.api.dto;


import roomescape.core.domain.reservation.Reservation;

public record ReservationId(
        Long value
) {

    public static ReservationId from(final Reservation reservation) {
        return new ReservationId(reservation.getId());
    }
}
