package roomescape.application.error.exception;


import roomescape.application.error.code.ApplicationErrorCode;
import roomescape.application.error.key.ApplicationErrorKeys;

public class ApplicationException extends RuntimeException {

    private final ApplicationErrorCode code;

    private final ApplicationErrorKeys keys;

    public ApplicationException(ApplicationErrorCode code, ApplicationErrorKeys keys) {
        super(keys.toMessage() + code.getMessage());
        this.code = code;
        this.keys = keys;
    }

    public ApplicationErrorCode getCode() {
        return code;
    }

    public String getCodeName() {
        return this.code.name();
    }
}
