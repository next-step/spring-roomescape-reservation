package roomescape.application.service.command;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.vo.ReservationDateTime;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;

import java.time.LocalDateTime;

public class CreateReservationCommand {

    private final String name;
    private final LocalDateTime reservationDateTime;

    public CreateReservationCommand(String name, LocalDateTime reservationDateTime) {
        this.name = name;
        this.reservationDateTime = reservationDateTime;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public Reservation toReservation() {
        return new Reservation(
                ReservationId.empty(),
                new ReservationName(this.name),
                new ReservationDateTime(this.getReservationDateTime())
        );
    }
}
