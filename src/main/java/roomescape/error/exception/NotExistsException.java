package roomescape.error.exception;

import roomescape.error.RoomescapeErrorMessage;

public class NotExistsException extends RuntimeException {

    public NotExistsException(String message) {
        super(message + RoomescapeErrorMessage.NOT_EXISTS_EXCEPTION);
    }
}
