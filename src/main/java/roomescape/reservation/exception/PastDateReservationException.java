package roomescape.reservation.exception;

public class PastDateReservationException extends RuntimeException {

    public PastDateReservationException(String message) {
        super(message);
    }
}
