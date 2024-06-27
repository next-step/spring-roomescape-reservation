package roomescape.domain.reservation.validator;

import roomescape.util.clockholder.ClockHolder;
import roomescape.domain.reservation.ReservationDateTime;
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
        ReservationDateTime reservationDateTime = new ReservationDateTime(date, time);

        if (reservationDateTime.isBefore(currentTime)) {
            throw CreateReservationValidateException.pastTime(reservationDateTime.toLocalDateTime());
        }
    }
}
