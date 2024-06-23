package roomescape.domain.reservationtime.exception;

import roomescape.domain.reservationtime.model.ReservationTime;

public class DupliactedReservationTimeException extends ReservationTimeException{

    public DupliactedReservationTimeException(final String message) {
        super(message);
    }

    public static DupliactedReservationTimeException from(ReservationTime reservationTime) {
        return new DupliactedReservationTimeException(
                "ReservationTime duplicated. %s".formatted(reservationTime.toString())
        );
    }
}
