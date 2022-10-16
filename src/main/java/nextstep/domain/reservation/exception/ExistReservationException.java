package nextstep.domain.reservation.exception;

public class ExistReservationException extends RuntimeException {
    public ExistReservationException(String message) {
        super(message);
    }
}