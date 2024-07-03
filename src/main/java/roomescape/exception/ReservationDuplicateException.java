package roomescape.exception;

public class ReservationDuplicateException extends RuntimeException {
    public ReservationDuplicateException(String message) {
        super(message);
    }
}
