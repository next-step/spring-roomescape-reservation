package roomescape.domain.error.exception;

import roomescape.domain.error.code.DomainErrorCode;

public class DomainException extends RuntimeException {

    private final DomainErrorCode code;

    public DomainException(DomainErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    public String getCodeName() {
        return this.code.name();
    }
}
