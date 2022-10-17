package nextstep.reservation.domain.exception;

public class DuplicatedReservationException extends RuntimeException {

    public DuplicatedReservationException(Long scheduleId, String date, String time) {
        super(String.format("아이디가 [%s]인 스케쥴이 [%s %s]에 이미 예약이 존재합니다.", scheduleId, date, time));
    }
}
