package roomescape.core.domain.reservation.exception;


import roomescape.core.domain.reservation.ReservationId;

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
