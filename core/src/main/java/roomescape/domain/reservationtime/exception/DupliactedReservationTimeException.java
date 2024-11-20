package roomescape.domain.reservationtime.exception;


import roomescape.domain.common.exception.BadRequestException;
import roomescape.domain.common.exception.CustomErrorCode;
import roomescape.domain.reservationtime.domain.ReservationTime;

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
