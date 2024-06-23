package roomescape.application.error.exception;

import roomescape.application.error.code.ApplicationErrorCode;
import roomescape.application.error.key.ApplicationErrorKey;
import roomescape.application.error.key.ApplicationErrorKeys;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservationtime.ReservationTime;

import java.util.List;

import static roomescape.application.error.code.ApplicationErrorCode.CANNOT_CREATE_EXIST_RESERVATION_AT_THIS_TIME;

public class CreateReservationValidateException extends ApplicationException {

    private static final String ERROR_KEY_NAME_DATE = "date";
    private static final String ERROR_KEY_TIME_ID = "timeId";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public CreateReservationValidateException(ApplicationErrorCode code, ApplicationErrorKeys keys) {
        super(code, keys);
    }

    public static CreateReservationValidateException existTime(
            ReservationDate reservationDate,
            ReservationTime reservationTime
    ) {
        return new CreateReservationValidateException(
                CANNOT_CREATE_EXIST_RESERVATION_AT_THIS_TIME,
                new ApplicationErrorKeys(
                        List.of(
                                new ApplicationErrorKey(ERROR_KEY_NAME_DATE, reservationDate.getFormatted(DATE_FORMAT)),
                                new ApplicationErrorKey(ERROR_KEY_TIME_ID, reservationTime.getId().toString())
                        )
                )
        );
    }
}
