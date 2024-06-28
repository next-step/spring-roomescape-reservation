package roomescape.domain.reservationtime.exception;

public class ReservationTimeException extends RuntimeException {

    public ReservationTimeException(final String message) {
        super(message);
    }

    public static ReservationTimeException nullField(String nullFieldName) {
        return new ReservationTimeException("Field '%s' must not be null".formatted(nullFieldName));
    }
}
