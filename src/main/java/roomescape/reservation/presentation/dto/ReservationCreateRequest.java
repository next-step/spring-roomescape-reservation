package roomescape.reservation.presentation.dto;

import java.time.LocalDate;

import roomescape.reservation.domain.Reservation;
import roomescape.theme.domain.Theme;
import roomescape.time.domain.ReservationTime;

public record ReservationCreateRequest(String name, String date, Long timeId, Long themeId) {

    public Reservation toReservation(ReservationTime savedReservationTime, Theme savedTheme) {
        return new Reservation(name, LocalDate.parse(date), savedReservationTime, savedTheme);
    }
}
