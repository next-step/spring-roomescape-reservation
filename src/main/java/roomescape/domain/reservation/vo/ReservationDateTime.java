package roomescape.domain.reservation.vo;

import roomescape.domain.validator.ObjectValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ReservationDateTime(LocalDateTime reservationDateTime) {

    public ReservationDateTime {
        ObjectValidator.validateNotNull(reservationDateTime);
    }

    public String fetchReservationDateTime(String pattern) {
        return reservationDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
