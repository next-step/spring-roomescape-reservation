package roomescape.domain.reservationtime.exception;

public class ReservationTimeAlreadyInUse extends ReservationTimeException{

    public ReservationTimeAlreadyInUse(final String message) {
        super(message);
    }
}
