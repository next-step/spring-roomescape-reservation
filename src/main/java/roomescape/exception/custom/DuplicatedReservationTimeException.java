package roomescape.exception.custom;

public class DuplicatedReservationTimeException extends RuntimeException {

    public DuplicatedReservationTimeException() {
        super();
    }

    public DuplicatedReservationTimeException(String message) {
        super(message);
    }

    public DuplicatedReservationTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedReservationTimeException(Throwable cause) {
        super(cause);
    }
}
