package roomescape.domain.reservation.service;

import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.error.exception.CreateReservationValidateException;

import java.time.LocalDateTime;

public class CreateReservationValidator {

    private final ReservationDate reservationDate;

    private final ReservationTime reservationTime;

    private final ClockHolder clockHolder;

    public CreateReservationValidator(
            ReservationDate reservationDate,
            ReservationTime reservationTime,
            ClockHolder clockHolder
    ) {
        this.reservationTime = reservationTime;
        this.reservationDate = reservationDate;
        this.clockHolder = clockHolder;
    }

    public void validate() {
        LocalDateTime currentTime = clockHolder.getCurrentTime();
        LocalDateTime startDateTime = LocalDateTime.of(reservationDate.date(), reservationTime.getStartAt());

        if (startDateTime.isBefore(currentTime)) {
            throw CreateReservationValidateException.pastTime(startDateTime);
        }
    }
}
