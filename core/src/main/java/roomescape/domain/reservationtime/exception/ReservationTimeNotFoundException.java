package roomescape.domain.reservationtime.exception;


import roomescape.domain.common.exception.CustomErrorCode;
import roomescape.domain.common.exception.NotFoundException;
import roomescape.domain.reservationtime.domain.ReservationTimeId;

import java.time.LocalTime;

public class ReservationTimeNotFoundException extends NotFoundException {

    public ReservationTimeNotFoundException(final String message) {
        super(CustomErrorCode.RT404, message);
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
