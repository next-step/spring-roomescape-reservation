package roomescape.core.domain.reservation.exception;


import roomescape.core.domain.reservation.ReservationId;

public class DuplicatedReservationException extends ReservationException {

    private DuplicatedReservationException(final String message) {
        super(message);
    }

    public static DuplicatedReservationException fromId(ReservationId id) {
        return new DuplicatedReservationException("Duplicated Reservations exists. (id=%d)".formatted(id.value()));
    }
}
