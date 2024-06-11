package roomescape.theme.error.exception;

import roomescape.global.error.exception.UserException;

public class ThemeException extends UserException {

    public ThemeException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getErrorMessage());
    }

    @Override
    public int getStatus() {
        return super.getStatus();
    }
}
