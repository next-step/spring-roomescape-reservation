package roomescape.error.exception;

import roomescape.error.RoomescapeErrorMessage;

public class ReferentialIntegrityException extends RuntimeException {

    private RoomescapeErrorMessage errorCode;

    public ReferentialIntegrityException(String message) {
        super(message);
        this.errorCode = RoomescapeErrorMessage.REFERENTIAL_EXCEPTION;
    }

    public RoomescapeErrorMessage getErrorCode() {
        return errorCode;
    }
}
