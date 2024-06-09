package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import roomescape.reservation.domain.Reservation;

public record ReservationResponse(Long reservationId, String name, LocalDate date, LocalTime time) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getTime().getStartAt());
    }
}
