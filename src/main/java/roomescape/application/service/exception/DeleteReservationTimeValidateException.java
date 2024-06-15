package roomescape.application.service.exception;

import roomescape.application.error.code.ErrorCode;

public class DeleteReservationTimeValidateException extends ServiceException {
    public DeleteReservationTimeValidateException(ErrorCode code) {
        super(code);
    }
}
