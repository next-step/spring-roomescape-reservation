package roomescape.reservation.presentation.dto;

import java.time.LocalDate;

import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

public record ReservationCreateRequest(String name, String date, Long timeId) {

    public Reservation toReservation(ReservationTime savedReservationTime) {
        return new Reservation(name, LocalDate.parse(date), savedReservationTime);
    }
}
