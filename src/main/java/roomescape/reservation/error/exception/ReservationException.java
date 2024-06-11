package roomescape.reservation.error.exception;

import roomescape.global.error.exception.UserException;

public class ReservationException extends UserException {

    public ReservationException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getErrorMessage());
    }

    @Override
    public int getStatus() {
        return super.getStatus();
    }
}
