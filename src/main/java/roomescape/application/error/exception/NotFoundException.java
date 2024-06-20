package roomescape.application.error.exception;

import roomescape.application.error.code.ApplicationErrorCode;

public class NotFoundException extends ApplicationException {

    public NotFoundException(ApplicationErrorCode code) {
        super(code);
    }

    public NotFoundException(ApplicationErrorCode code, String message) {
        super(code, message);
    }
}
