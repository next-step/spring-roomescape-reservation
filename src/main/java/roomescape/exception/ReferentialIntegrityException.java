package roomescape.exception;

public class ReferentialIntegrityException extends RuntimeException {

    private RoomescapeErrorCode errorCode;

    public ReferentialIntegrityException(String message) {
        super(message);
        this.errorCode = RoomescapeErrorCode.REFERENTIAL_EXCEPTION;
    }

    public RoomescapeErrorCode getErrorCode() {
        return errorCode;
    }
}