package roomescape.exception;

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
