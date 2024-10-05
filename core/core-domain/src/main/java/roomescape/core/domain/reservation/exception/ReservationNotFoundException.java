package roomescape.core.domain.reservation.exception;


import roomescape.core.domain.common.exception.CustomErrorCode;
import roomescape.core.domain.common.exception.NotFoundException;
import roomescape.core.domain.reservation.ReservationId;

public class ReservationNotFoundException extends NotFoundException {

    public ReservationNotFoundException(final String message) {
        super(CustomErrorCode.R404, message);
    }

    public static RuntimeException from(final ReservationId reservationId) {
        return new ReservationNotFoundException(
                "Cannot find Reservation matching id=%d".formatted(reservationId.value())
        );
    }
}
