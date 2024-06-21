package roomescape.domain.error.exception;

import roomescape.domain.error.code.DomainErrorCode;

public class CreateReservationValidateException extends DomainException {

    public CreateReservationValidateException(
            DomainErrorCode domainErrorCode,
            String message
    ) {
        super(domainErrorCode, message);
    }
}
