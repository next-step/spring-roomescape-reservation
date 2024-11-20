package roomescape.domain.reservation.exception;

import roomescape.domain.common.exception.BadRequestException;
import roomescape.domain.common.exception.CustomErrorCode;

public class ReservationAlreadyCanceledException extends BadRequestException {

    public ReservationAlreadyCanceledException() {
        super(CustomErrorCode.R406, CustomErrorCode.R406.getDescription());
    }
}
