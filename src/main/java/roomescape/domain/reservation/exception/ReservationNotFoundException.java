package roomescape.domain.reservation.exception;

import roomescape.domain.reservation.dto.ReservationId;

public class ReservationNotFoundException extends ReservationException {

    public ReservationNotFoundException(final String message) {
        super(message);
    }

    public static RuntimeException from(final ReservationId reservationId) {
        return new ReservationNotFoundException(
                "Cannot find Reservation matching id=%d".formatted(reservationId.value())
        );
    }
}
