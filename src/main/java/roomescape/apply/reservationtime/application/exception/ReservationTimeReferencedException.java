package roomescape.apply.reservationtime.application.exception;

public class ReservationTimeReferencedException extends IllegalArgumentException {

    public static final String DEFAULT_MESSAGE = "예약 시간이 다른 예약에서 참조되고 있어 삭제할 수 없습니다.";

    public ReservationTimeReferencedException() {
        super(DEFAULT_MESSAGE);
    }
}
