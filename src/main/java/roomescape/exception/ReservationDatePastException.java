package roomescape.exception;

public class ReservationDatePastException extends RuntimeException {
    public ReservationDatePastException(String message) {
        super(message);
    }
}
