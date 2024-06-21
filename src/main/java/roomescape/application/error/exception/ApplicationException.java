package roomescape.application.error.exception;

import roomescape.application.error.code.ApplicationErrorCode;

public class ApplicationException extends RuntimeException {

    private final ApplicationErrorCode code;

    public ApplicationException(ApplicationErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public ApplicationException(ApplicationErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    public ApplicationErrorCode getCode() {
        return code;
    }

    public String getCodeName() {
        return this.code.name();
    }
}
