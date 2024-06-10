package roomescape.controller.dto;

import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;

public record ReservationRequest(String name, String date, long timeId, long themeId) {

	public static void validateReservation(ReservationRequest request) {
		if (request.name() == null) {
			throw new RoomEscapeException(ErrorCode.INVALID_RESERVATION);
		}

		if (request.date() == null) {
			throw new RoomEscapeException(ErrorCode.INVALID_TIME);
		}
	}

}
