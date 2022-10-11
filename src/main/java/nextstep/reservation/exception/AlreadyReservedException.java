package nextstep.reservation.exception;

public class AlreadyReservedException extends IllegalArgumentException {
    private static final String MESSAGE = "이미 예약되어 있습니다.";

    public AlreadyReservedException() {
        super(MESSAGE);
    }
}
