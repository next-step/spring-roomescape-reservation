package roomescape.controller.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationResponse(Long id, String name, String date, ReservationTimeResponse time) {

	public static ReservationResponse from(Reservation reservation, ReservationTime reservationTime) {
		return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
				ReservationTimeResponse.from(reservationTime));
	}

}
