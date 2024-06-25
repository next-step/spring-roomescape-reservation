package roomescape.error.exception;

import roomescape.error.code.DomainErrorCode;
import roomescape.error.key.DomainErrorKeys;

public class DomainException extends RuntimeException {

    private final DomainErrorCode code;

    private final DomainErrorKeys keys;

    public DomainException(DomainErrorCode code, DomainErrorKeys keys) {
        super(keys.toMessage() + code.getMessage());
        this.code = code;
        this.keys = keys;
    }

    public String getCodeName() {
        return this.code.name();
    }
}
