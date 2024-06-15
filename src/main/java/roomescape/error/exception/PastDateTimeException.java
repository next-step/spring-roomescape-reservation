package roomescape.error.exception;

import roomescape.error.RoomescapeErrorMessage;

public class PastDateTimeException extends RuntimeException {

    public PastDateTimeException() {
        super(RoomescapeErrorMessage.PAST_DATETIME_EXCEPTION);
    }
}
