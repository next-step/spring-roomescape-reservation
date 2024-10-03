package roomescape.core.domain.reservationtime.exception;


import roomescape.core.domain.common.exception.BadRequestException;
import roomescape.core.domain.reservationtime.ReservationTime;

public class DupliactedReservationTimeException extends BadRequestException {

    public DupliactedReservationTimeException(final String message) {
        super(message);
    }

    public static DupliactedReservationTimeException from(ReservationTime reservationTime) {
        return new DupliactedReservationTimeException(
                "ReservationTime duplicated. %s".formatted(reservationTime.toString())
        );
    }
}
