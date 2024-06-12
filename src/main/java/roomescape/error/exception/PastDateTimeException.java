package roomescape.error.exception;

import roomescape.error.RoomescapeErrorMessage;

public class PastDateTimeException extends RuntimeException {

    private RoomescapeErrorMessage errorCode;

    public PastDateTimeException(String message) {
        super(message);
        this.errorCode = RoomescapeErrorMessage.PAST_DATETIME_EXCEPTION;
    }

    public PastDateTimeException() {
        this("");
    }

    public RoomescapeErrorMessage getErrorCode() {
        return errorCode;
    }
}
