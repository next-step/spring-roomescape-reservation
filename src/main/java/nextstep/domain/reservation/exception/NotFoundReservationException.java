package nextstep.domain.reservation.exception;

public class NotFoundReservationException extends RuntimeException {
    public NotFoundReservationException() {
        super("예약 정보를 찾을 수 없습니다.");
    }
}
