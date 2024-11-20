package roomescape.domain.reservationtime.exception;

import roomescape.domain.common.exception.BadRequestException;
import roomescape.domain.common.exception.CustomErrorCode;

public class ReservationTimeOutOfRangeException extends BadRequestException {

    public ReservationTimeOutOfRangeException(final String message) {
        super(CustomErrorCode.RT407, message);
    }
}
