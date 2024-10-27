package roomescape.domain.reservationtime.exception;

import roomescape.domain.common.exception.BadRequestException;
import roomescape.domain.common.exception.CustomErrorCode;

public class ReservationTimeAlreadyInUse extends BadRequestException {

    public ReservationTimeAlreadyInUse(final String message) {
        super(CustomErrorCode.RT406, message);
    }
}
