package roomescape.apply.reservation.ui.dto;

import roomescape.apply.reservation.domain.Reservation;
import roomescape.apply.reservationtime.domain.ReservationTime;
import roomescape.apply.reservationtime.ui.dto.ReservationTimeResponse;
import roomescape.apply.theme.domain.Theme;
import roomescape.apply.theme.ui.dto.ThemeResponse;

public record ReservationResponse(
        long id,
        String name,
        String date,
        ThemeResponse theme,
        ReservationTimeResponse time
) {
    public static ReservationResponse from(Reservation reservation, Theme theme, ReservationTime reservationTime) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                ThemeResponse.from(theme), ReservationTimeResponse.from(reservationTime));
    }
}
