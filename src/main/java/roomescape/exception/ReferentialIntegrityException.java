package roomescape.exception;

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
