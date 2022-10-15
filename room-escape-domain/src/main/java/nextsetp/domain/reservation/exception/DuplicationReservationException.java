package nextsetp.domain.reservation.exception;

import nextsetp.common.BusinessException;

public class DuplicationReservationException extends BusinessException {
    private static final String MESSAGE = "해당 시간에 예약이 이미 존재합니다.";

    public DuplicationReservationException() {
        super(MESSAGE);
    }
}
