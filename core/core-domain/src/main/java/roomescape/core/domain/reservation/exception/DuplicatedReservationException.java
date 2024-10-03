package roomescape.core.domain.reservation.exception;


import roomescape.core.domain.common.exception.BadRequestException;
import roomescape.core.domain.reservation.ReservationId;

public class DuplicatedReservationException extends BadRequestException {

    private DuplicatedReservationException(final String message) {
        super(message);
    }

    public static DuplicatedReservationException fromId(ReservationId id) {
        return new DuplicatedReservationException("Duplicated Reservations exists. (id=%d)".formatted(id.value()));
    }
}
