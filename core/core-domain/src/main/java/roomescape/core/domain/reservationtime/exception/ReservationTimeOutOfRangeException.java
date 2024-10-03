package roomescape.core.domain.reservationtime.exception;

import roomescape.core.domain.common.exception.BadRequestException;

public class ReservationTimeOutOfRangeException extends BadRequestException {

    public ReservationTimeOutOfRangeException(final String message) {
        super(message);
    }
}
