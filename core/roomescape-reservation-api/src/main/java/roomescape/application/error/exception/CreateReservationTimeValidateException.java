package roomescape.application.error.exception;

import roomescape.application.error.code.ApplicationErrorCode;
import roomescape.application.error.key.ApplicationErrorKey;
import roomescape.application.error.key.ApplicationErrorKeys;
import roomescape.domain.reservationtime.ReservationTime;

import static roomescape.application.error.code.ApplicationErrorCode.CANNOT_CREATE_EXIST_RESERVATION_TIME;

public class CreateReservationTimeValidateException extends ApplicationException {

    private static final String TIME_FORMAT = "HH:mm";

    private static final String ERROR_KEY_NAME_START_AT = "startAt";

    public CreateReservationTimeValidateException(ApplicationErrorCode code, ApplicationErrorKeys keys) {
        super(code, keys);
    }

    public static CreateReservationTimeValidateException existTime(ReservationTime reservationTime) {
        return new CreateReservationTimeValidateException(
                CANNOT_CREATE_EXIST_RESERVATION_TIME,
                ApplicationErrorKeys.of(
                        new ApplicationErrorKey(
                                ERROR_KEY_NAME_START_AT,
                                reservationTime.getFormattedStartAt(TIME_FORMAT)
                        ))
        );
    }
}
