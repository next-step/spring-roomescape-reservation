package roomescape.reservation.presentation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

public record ReservationCreateRequest(LocalDate date, String name, LocalTime time) {

    public Reservation toReservation(ReservationTime savedReservationTime) {
        return new Reservation(name, date, savedReservationTime);
    }

    public ReservationTime toReservationTime() {
        return new ReservationTime(time);
    }
}
