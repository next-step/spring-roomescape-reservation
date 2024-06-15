package roomescape.error.exception;

import roomescape.error.RoomescapeErrorMessage;

public class ReferenceException extends RuntimeException {

    public ReferenceException(String message) {
        super(message + RoomescapeErrorMessage.REFERENTIAL_EXCEPTION);
    }
}
