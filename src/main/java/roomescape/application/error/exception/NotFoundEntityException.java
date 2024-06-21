package roomescape.application.error.exception;

import roomescape.application.error.code.ApplicationErrorCode;

public class NotFoundEntityException extends NotFoundException {
    public NotFoundEntityException(ApplicationErrorCode code) {
        super(code);
    }

    public NotFoundEntityException(ApplicationErrorCode code, String message) {
        super(code, message);
    }
}
