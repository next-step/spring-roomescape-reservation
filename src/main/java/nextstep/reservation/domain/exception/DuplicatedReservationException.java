package nextstep.reservation.domain.exception;

public class DuplicatedReservationException extends RuntimeException {

    public DuplicatedReservationException(String date, String time) {
        super(String.format("해당 시간에 이미 예약이 존재합니다. [%s %s]", date, time));
    }
}
