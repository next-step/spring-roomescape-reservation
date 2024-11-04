package roomescape.domain.reservation.api.dto;


import roomescape.domain.reservation.domain.Reservation;

public record ReservationId(
        Long value
) {

    public static ReservationId from(final Reservation reservation) {
        return new ReservationId(reservation.getId());
    }
}
