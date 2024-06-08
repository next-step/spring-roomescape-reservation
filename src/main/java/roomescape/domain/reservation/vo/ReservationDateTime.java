package roomescape.domain.reservation.vo;

import roomescape.domain.validator.ObjectValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationDateTime {

    private final LocalDateTime reservationDateTime;

    public ReservationDateTime(LocalDateTime reservationDateTime) {
        ObjectValidator.validateNotNull(reservationDateTime);
        this.reservationDateTime = reservationDateTime;
    }

    public String fetchReservationDateTime(String pattern) {
        return reservationDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }
}
