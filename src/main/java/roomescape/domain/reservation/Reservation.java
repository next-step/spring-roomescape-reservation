package roomescape.domain.reservation;

import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.validator.ObjectValidator;

import java.time.LocalDate;

public class Reservation {

    private final ReservationId id;

    private final ReservationName reservationName;

    private final ReservationDate reservationDate;

    private final ReservationTimeId reservationTimeId;

    public Reservation(
            ReservationId id,
            ReservationName reservationName,
            ReservationDate reservationDate,
            ReservationTimeId reservationTimeId
    ) {
        ObjectValidator.validateNotNull(id, reservationName, reservationDate, reservationTimeId);
        this.id = id;
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.reservationTimeId = reservationTimeId;
    }

    public Long getId() {
        return id.id();
    }

    public String getReservationName() {
        return reservationName.name();
    }

    public String getFormattedReservationDate(String pattern) {
        return this.reservationDate.getFormatted(pattern);
    }

    public LocalDate getReservationDate() {
        return this.reservationDate.getDate();
    }

    public Long getReservationTimeId() {
        return this.reservationTimeId.getId();
    }
}
