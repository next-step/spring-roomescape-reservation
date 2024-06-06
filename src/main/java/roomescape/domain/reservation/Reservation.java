package roomescape.domain.reservation;

import roomescape.domain.reservation.vo.ReservationDateTime;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.validator.ObjectValidator;

public class Reservation {

    private final ReservationId id;

    private final ReservationName name;

    private final ReservationDateTime reservationDateTime;


    public Reservation(ReservationId id, ReservationName name, ReservationDateTime reservationDateTime) {
        ObjectValidator.validateNotNull(id, name, reservationDateTime);
        this.id = id;
        this.name = name;
        this.reservationDateTime = reservationDateTime;
    }

    public Long getId() {
        return id.id();
    }

    public String getName() {
        return name.name();
    }

    public String fetchReservationDateTime(String pattern) {
        return reservationDateTime.fetchReservationDateTime(pattern);
    }
}
