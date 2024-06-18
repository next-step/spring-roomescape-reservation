package roomescape.domain.reservation;

import roomescape.domain.reservation.service.CreateReservationValidator;
import roomescape.domain.reservation.service.SystemClockHolder;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.validator.ObjectValidator;

import java.time.LocalDate;

public class Reservation {

    private final ReservationId id;

    private final ReservationName reservationName;

    private final ReservationDate reservationDate;

    private final ReservationTime reservationTime;


    public Reservation(
            ReservationId id,
            ReservationName reservationName,
            ReservationDate reservationDate,
            ReservationTime reservationTime
    ) {
        ObjectValidator.validateNotNull(id, reservationName, reservationDate, reservationTime);
        this.id = id;
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public static Reservation create(
            ReservationId id,
            ReservationName reservationName,
            ReservationDate reservationDate,
            ReservationTime reservationTime
    ) {
        CreateReservationValidator validator = new CreateReservationValidator(
                reservationDate,
                reservationTime,
                new SystemClockHolder()
        );
        validator.validate();

        return new Reservation(
                id,
                reservationName,
                reservationDate,
                reservationTime
        );
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
        return this.reservationDate.date();
    }

    public Long getReservationTimeId() {
        return reservationTime.getId();
    }
}
