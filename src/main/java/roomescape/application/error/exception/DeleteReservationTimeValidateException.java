package roomescape.application.error.exception;

import roomescape.application.error.code.ApplicationErrorCode;

public class DeleteReservationTimeValidateException extends ApplicationException {
    public DeleteReservationTimeValidateException(ApplicationErrorCode code) {
        super(code);
    }
}
