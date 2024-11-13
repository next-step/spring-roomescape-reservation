package roomescape.domain.reservationtime.exception;

import roomescape.domain.common.exception.BadRequestException;
import roomescape.domain.common.exception.CustomErrorCode;
import roomescape.domain.reservationtime.domain.ReservationTime;

public class ReservationTimeAlreadyInUse extends BadRequestException {

    private static final String ERROR_MESSAGE_FORMAT = "ReservationTime(id=%d) is already in use";

    public ReservationTimeAlreadyInUse(final String message) {
        super(CustomErrorCode.RT406, message);
    }

    public static ReservationTimeAlreadyInUse from(final ReservationTime reservationTime) {
        return new ReservationTimeAlreadyInUse(ERROR_MESSAGE_FORMAT.formatted(reservationTime.getId().value()));
    }
}
