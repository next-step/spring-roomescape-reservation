package roomescape.exception;

public class PastDateTimeException extends RuntimeException {

    private RoomescapeErrorCode errorCode;

    public PastDateTimeException(String message) {
        super(message);
        this.errorCode = RoomescapeErrorCode.PAST_DATETIME_EXCEPTION;
    }

    public PastDateTimeException() {
        this("");
    }

    public RoomescapeErrorCode getErrorCode() {
        return errorCode;
    }
}
