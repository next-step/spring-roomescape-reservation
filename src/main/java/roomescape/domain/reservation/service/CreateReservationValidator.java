package roomescape.domain.reservation.service;

import roomescape.domain.error.exception.CreateReservationValidateException;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationDateTime;
import roomescape.domain.reservationtime.ReservationTime;

import java.time.LocalDateTime;

import static roomescape.domain.error.code.DomainErrorCode.CANNOT_CREATE_RESERVATION_FOR_PAST_TIME;

public class CreateReservationValidator {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

    private final ReservationTime reservationTime;

    private final ReservationDate reservationDate;

    private final ClockHolder clockHolder;

    public CreateReservationValidator(
            ReservationTime reservationTime,
            ReservationDate reservationDate,
            ClockHolder clockHolder
    ) {
        this.reservationTime = reservationTime;
        this.reservationDate = reservationDate;
        this.clockHolder = clockHolder;
    }

    public void validate() {
        LocalDateTime currentTime = clockHolder.getCurrentTime();
        ReservationDateTime reservationDateTime = ReservationDateTime.of(reservationDate, reservationTime);

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
