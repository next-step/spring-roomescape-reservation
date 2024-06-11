package roomescape.time.error.exception;

import roomescape.global.error.exception.UserException;

public class TimeException extends UserException {

    public TimeException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getErrorMessage());
    }

    @Override
    public int getStatus() {
        return super.getStatus();
    }
}
