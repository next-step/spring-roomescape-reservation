package roomescape.domain.reservation.service;

import roomescape.domain.error.exception.CreateReservationValidateException;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationDateTime;
import roomescape.domain.reservationtime.ReservationTime;

import java.time.LocalDateTime;

import static roomescape.domain.error.code.DomainErrorCode.CANNOT_CREATE_RESERVATION_FOR_PAST_TIME;

public class CreateReservationValidator {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

    private final ReservationTime time;

    private final ReservationDate date;

    private final ClockHolder clockHolder;

    public CreateReservationValidator(
            ReservationTime time,
            ReservationDate date,
            ClockHolder clockHolder
    ) {
        this.time = time;
        this.date = date;
        this.clockHolder = clockHolder;
    }

    public void validate() {
        LocalDateTime currentTime = clockHolder.getCurrentTime();
        ReservationDateTime reservationDateTime = ReservationDateTime.of(date, time);

        if (reservationDateTime.isBefore(currentTime)) {
            throw new CreateReservationValidateException(
                    CANNOT_CREATE_RESERVATION_FOR_PAST_TIME,
                    String.format(
                            "[예약 시간 : %s] 해당 예약 시간은 이미 지나갔습니다.",
                            reservationDateTime.getFormatted(DATETIME_FORMAT)
                    )
            );
        }
    }
}
