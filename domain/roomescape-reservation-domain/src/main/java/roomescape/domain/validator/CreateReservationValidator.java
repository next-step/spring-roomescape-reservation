package roomescape.domain.validator;

import roomescape.domain.ClockHolder;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.error.exception.CreateReservationValidateException;

import java.time.LocalDateTime;

public final class CreateReservationValidator {

    private CreateReservationValidator() {
        throw new UnsupportedOperationException(this.getClass().getName() + "의 인스턴스는 생성되어서 안됩니다.");
    }

    public static void validate(ReservationDate date, ReservationTime time, ClockHolder clockHolder) {
        LocalDateTime currentTime = clockHolder.getCurrentTime();
        LocalDateTime startDateTime = LocalDateTime.of(date.date(), time.getStartAt());

        if (startDateTime.isBefore(currentTime)) {
            throw CreateReservationValidateException.pastTime(startDateTime);
        }
    }
}
