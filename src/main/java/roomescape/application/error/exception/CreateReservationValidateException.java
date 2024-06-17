package roomescape.application.error.exception;

import roomescape.application.error.code.ApplicationErrorCode;

public class CreateReservationValidateException extends ApplicationException {

    public CreateReservationValidateException(ApplicationErrorCode code) {
        super(code);
    }

    public CreateReservationValidateException(ApplicationErrorCode code, String message) {
        super(code, message);
    }
}
