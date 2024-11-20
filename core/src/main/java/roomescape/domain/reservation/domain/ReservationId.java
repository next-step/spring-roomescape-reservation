package roomescape.domain.reservation.domain;

import java.util.Objects;

public record ReservationId(
        Long value
) {

    public ReservationId {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("value must not be null");
        }
    }

    public static ReservationId from(final Reservation reservation) {
        return new ReservationId(reservation.getId());
    }
}
