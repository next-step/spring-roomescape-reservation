package roomescape.core.domain.reservationtime;

import roomescape.core.domain.reservationtime.exception.ReservationTimeException;

import java.util.Objects;

public record ReservationTimeId(
        Long value
) {

    public ReservationTimeId {
        if (Objects.isNull(value)) {
            throw ReservationTimeException.nullField("value");
        }
    }
}
