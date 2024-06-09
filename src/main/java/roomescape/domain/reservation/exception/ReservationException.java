package roomescape.domain.reservation.exception;

public class ReservationException extends RuntimeException {

    public ReservationException(final String message) {
        super(message);
    }

    public static ReservationException nullField(String nullFieldName) {
        return new ReservationException("Field '%s' must not be null".formatted(nullFieldName));
    }

}
