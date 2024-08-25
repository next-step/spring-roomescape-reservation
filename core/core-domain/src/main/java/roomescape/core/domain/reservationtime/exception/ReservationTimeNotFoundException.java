package roomescape.core.domain.reservationtime.exception;


import roomescape.core.domain.reservationtime.ReservationTimeId;

import java.time.LocalTime;

public class ReservationTimeNotFoundException extends ReservationTimeException {

    public ReservationTimeNotFoundException(final String message) {
        super(message);
    }

    public static ReservationTimeNotFoundException fromId(ReservationTimeId id) {
        return new ReservationTimeNotFoundException(
                "Cannot find ReservationTime for id=%d".formatted(id.value())
        );
    }

    public static RuntimeException fromStartAt(final LocalTime startAt) {
        return new ReservationTimeNotFoundException(
                "Cannot find ReservationTime for startAt=%s".formatted(startAt)
        );
    }
}
