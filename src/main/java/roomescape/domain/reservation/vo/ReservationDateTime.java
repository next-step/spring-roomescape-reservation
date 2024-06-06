package roomescape.domain.reservation.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationDateTime {

    private final LocalDateTime reservationDateTime;

    public ReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    public String fetchReservationDateTime(String pattern) {
        return reservationDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
