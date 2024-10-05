package roomescape.core.domain.reservationtime.exception;

import roomescape.core.domain.common.exception.BadRequestException;
import roomescape.core.domain.common.exception.CustomErrorCode;

public class ReservationTimeAlreadyInUse extends BadRequestException {

    public ReservationTimeAlreadyInUse(final String message) {
        super(CustomErrorCode.RT406, message);
    }
}
