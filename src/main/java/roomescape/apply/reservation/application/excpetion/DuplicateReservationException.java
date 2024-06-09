package roomescape.apply.reservation.application.excpetion;

public class DuplicateReservationException extends IllegalArgumentException {

    public static final String DEFAULT_MESSAGE = "해당 테마의 시간은 이미 예약되었습니다.";

    public DuplicateReservationException() {
        super(DEFAULT_MESSAGE);
    }
}
