package roomescape.core.domain.reservationtime.exception;

import roomescape.core.domain.common.exception.BadRequestException;
import roomescape.core.domain.common.exception.CustomErrorCode;

public class ReservationTimeOutOfRangeException extends BadRequestException {

    public ReservationTimeOutOfRangeException(final String message) {
        super(CustomErrorCode.RT407, message);
    }
}
