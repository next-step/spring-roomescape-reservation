package roomescape.core.domain.reservationtime.exception;

import roomescape.core.domain.common.exception.BadRequestException;

public class ReservationTimeAlreadyInUse extends BadRequestException {

    public ReservationTimeAlreadyInUse(final String message) {
        super(message);
    }
}
