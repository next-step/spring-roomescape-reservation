package roomescape.domain.reservation.vo;

import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.validator.ObjectValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ReservationDateTime(LocalDateTime dateTime) {

    public ReservationDateTime {
        ObjectValidator.validateNotNull(dateTime);
    }

    public static ReservationDateTime of(ReservationDate date, ReservationTime time) {
        return new ReservationDateTime(
                LocalDateTime.of(date.date(), time.getStartAt())
        );
    }

    public String getFormatted(String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public boolean isBefore(LocalDateTime localDateTime) {
        return this.dateTime.isBefore(localDateTime);
    }
}
