package roomescape.domain.reservationtime.exception;

import roomescape.domain.reservationtime.model.ReservationTimeId;

public class ReservationTimeNotFoundException extends ReservationTimeException{

    public ReservationTimeNotFoundException(final String message) {
        super(message);
    }

    public static ReservationTimeNotFoundException fromId(ReservationTimeId id) {
        return new ReservationTimeNotFoundException(
                "Cannot find ReservationTime for id=%d".formatted(id.getValue())
        );
    }
}
