package roomescape.application.service.command;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.vo.ReservationTimeId;

import java.time.LocalDate;

public class CreateReservationCommand {

    private final String reservationName;

    private final LocalDate reservationDate;

    private final Long reservationTimeId;

    public CreateReservationCommand(String reservationName, LocalDate reservationDate, Long reservationTimeId) {
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.reservationTimeId = reservationTimeId;
    }

    public Reservation toReservation() {
        return new Reservation(
                ReservationId.empty(),
                new ReservationName(this.reservationName),
                new ReservationDate(this.reservationDate),
                new ReservationTimeId(this.reservationTimeId)
        );
    }
}
