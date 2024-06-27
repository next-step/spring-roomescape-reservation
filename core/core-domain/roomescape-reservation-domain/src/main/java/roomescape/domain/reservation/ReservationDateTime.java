package roomescape.domain.reservation;

import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.util.validator.ObjectValidator;

import java.time.LocalDateTime;

public final class ReservationDateTime {

    private final ReservationDate date;
    private final ReservationTime time;


    public ReservationDateTime(ReservationDate date, ReservationTime time) {
        ObjectValidator.validateNotNull(date, time);
        this.date = date;
        this.time = time;
    }

    public boolean isBefore(LocalDateTime dateTime) {
        return this.toLocalDateTime().isBefore(dateTime);
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(date.date(), time.getStartAt());
    }

}
