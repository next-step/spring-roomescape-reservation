package roomescape.core.domain.reservationtime.exception;


import roomescape.core.domain.common.exception.BadRequestException;
import roomescape.core.domain.common.exception.CustomErrorCode;
import roomescape.core.domain.reservationtime.ReservationTime;

public class DupliactedReservationTimeException extends BadRequestException {

    public DupliactedReservationTimeException(final String message) {
        super(CustomErrorCode.RT405, message);
    }

    public static DupliactedReservationTimeException from(ReservationTime reservationTime) {
        return new DupliactedReservationTimeException(
                "ReservationTime duplicated. %s".formatted(reservationTime.toString())
        );
    }
}
