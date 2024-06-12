package roomescape.error.exception;

import roomescape.error.RoomescapeErrorMessage;

public class NotExistsException extends RuntimeException {

    private RoomescapeErrorMessage errorCode;

    public NotExistsException(String message) {
        super(message);
        this.errorCode = RoomescapeErrorMessage.NOT_EXISTS_EXCEPTION;
    }

    public RoomescapeErrorMessage getErrorCode() {
        return errorCode;
    }
}
