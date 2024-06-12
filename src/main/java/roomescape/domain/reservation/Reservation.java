package roomescape.domain.reservation;

import roomescape.domain.reservation.vo.ReservationDateTime;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.validator.ObjectValidator;

import java.time.LocalDateTime;

public class Reservation {

    private final ReservationId id;

    private final ReservationName reservationName;

    private final ReservationDateTime reservationDateTime;

    public Reservation(ReservationId id, ReservationName reservationName, ReservationDateTime reservationDateTime) {
        ObjectValidator.validateNotNull(reservationName, reservationDateTime);
        this.id = id;
        this.reservationName = reservationName;
        this.reservationDateTime = reservationDateTime;
    }

    public Long getId() {
        return id.id();
    }

    public String getReservationName() {
        return reservationName.name();
    }

    public String fetchReservationDateTime(String pattern) {
        return reservationDateTime.getFormatted(pattern);
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime.dateTime();
    }
}
