package roomescape.domain.error.exception;

import roomescape.domain.error.code.DomainErrorCode;
import roomescape.domain.error.key.DomainErrorKeys;

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
