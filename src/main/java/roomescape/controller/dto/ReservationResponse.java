package roomescape.controller.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.Theme;

public record ReservationResponse(Long id, String name, String date, ReservationTimeResponse time,
		ThemeResponse theme) {

	public static ReservationResponse from(Reservation reservation, ReservationTime reservationTime, Theme theme) {
		return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
				ReservationTimeResponse.from(reservationTime), ThemeResponse.from(theme));
	}

}
