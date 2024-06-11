package roomescape.time.global.error.exception;

public class InvalidValueException extends UserException {

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getErrorMessage());
    }

    @Override
    public int getStatus() {
        return super.getStatus();
    }
}
