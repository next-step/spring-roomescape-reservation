package roomescape.application.error.exception;

import roomescape.application.error.code.ApplicationErrorCode;

public class CreateReservationTimeValidateException extends ApplicationException {
    public CreateReservationTimeValidateException(ApplicationErrorCode code) {
        super(code);
    }

    public CreateReservationTimeValidateException(ApplicationErrorCode code, String message) {
        super(code, message);
    }
}
