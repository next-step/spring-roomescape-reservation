package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import roomescape.reservation.domain.Reservation;

public record ReservationResponse(Long id, String name, String themeName, LocalDate date, LocalTime startAt) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getTheme().getName(),reservation.getDate(),
                reservation.getTime().getStartAt());
    }
}
