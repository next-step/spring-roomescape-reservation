package roomescape.exception.custom;

public class DuplicatedReservationTime extends RuntimeException {

    public DuplicatedReservationTime() {
        super();
    }

    public DuplicatedReservationTime(String message) {
        super(message);
    }

    public DuplicatedReservationTime(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedReservationTime(Throwable cause) {
        super(cause);
    }
}
