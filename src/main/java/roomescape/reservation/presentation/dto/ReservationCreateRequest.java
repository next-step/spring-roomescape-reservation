package roomescape.reservation.presentation.dto;

import java.time.LocalDate;

import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

public record ReservationCreateRequest(LocalDate date, String name, Long timeId) {

    public Reservation toReservation(ReservationTime savedReservationTime) {
        return new Reservation(name, date, savedReservationTime);
    }
}
