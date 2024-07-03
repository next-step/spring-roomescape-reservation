package roomescape.exception;

public class ReservationExistException extends RuntimeException {
    public ReservationExistException(String message) {
        super(message);
    }
}
