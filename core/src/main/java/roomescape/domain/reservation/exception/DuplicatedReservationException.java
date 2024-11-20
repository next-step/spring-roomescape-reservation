package roomescape.domain.reservation.exception;


import roomescape.domain.common.exception.BadRequestException;
import roomescape.domain.common.exception.CustomErrorCode;
import roomescape.domain.reservation.domain.ReservationId;

public class DuplicatedReservationException extends BadRequestException {

    private DuplicatedReservationException(final String message) {
        super(CustomErrorCode.R405, message);
    }

    public static DuplicatedReservationException fromId(ReservationId id) {
        return new DuplicatedReservationException("Duplicated Reservations exists. (id=%d)".formatted(id.value()));
    }
}
