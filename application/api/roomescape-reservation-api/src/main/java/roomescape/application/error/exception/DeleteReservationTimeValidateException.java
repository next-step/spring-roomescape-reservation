package roomescape.application.error.exception;

import roomescape.application.error.code.ApplicationErrorCode;
import roomescape.application.error.key.ApplicationErrorKey;
import roomescape.application.error.key.ApplicationErrorKeys;
import roomescape.domain.reservationtime.vo.ReservationTimeId;

import static roomescape.application.error.code.ApplicationErrorCode.CANNOT_DELETE_EXIST_RESERVATION_AT_THIS_TIME;

public class DeleteReservationTimeValidateException extends ApplicationException {

    private static final String ERROR_KEY_TIME_ID = "timeId";

    public DeleteReservationTimeValidateException(ApplicationErrorCode code, ApplicationErrorKeys keys) {
        super(code, keys);
    }

    public static DeleteReservationTimeValidateException existReservation(ReservationTimeId reservationTimeId) {
        return new DeleteReservationTimeValidateException(
                CANNOT_DELETE_EXIST_RESERVATION_AT_THIS_TIME,
                ApplicationErrorKeys.of(
                        new ApplicationErrorKey(ERROR_KEY_TIME_ID, reservationTimeId.id().toString())
                )
        );
    }
}
