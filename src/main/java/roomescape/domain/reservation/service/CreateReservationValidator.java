package roomescape.domain.reservation.service;

import roomescape.domain.error.exception.CreateReservationValidateException;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservationtime.ReservationTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static roomescape.domain.error.code.DomainErrorCode.CANNOT_CREATE_RESERVATION_FOR_PAST_TIME;

public class CreateReservationValidator {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

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
            throw new CreateReservationValidateException(
                    CANNOT_CREATE_RESERVATION_FOR_PAST_TIME,
                    String.format(
                            "[예약 시간 : %s] 해당 예약 시간은 이미 지나갔습니다.",
                            startDateTime.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT))
                    )
            );
        }
    }
}
