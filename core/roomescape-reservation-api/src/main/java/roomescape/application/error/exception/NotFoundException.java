package roomescape.application.error.exception;

import roomescape.application.error.code.ApplicationErrorCode;
import roomescape.application.error.key.ApplicationErrorKeys;

public class NotFoundException extends ApplicationException {

    public NotFoundException(ApplicationErrorCode code, ApplicationErrorKeys keys) {
        super(code, keys);
    }
}
