package roomescape.error.exception;

public class ReservationTimeNotExistsException extends NotExistsException{

    public ReservationTimeNotExistsException() {
        super("해당 예약 시간이");
    }
}
