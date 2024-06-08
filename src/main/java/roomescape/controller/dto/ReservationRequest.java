package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;

public record ReservationRequest(String name, String date, long timeId, long themeId) {

	public static void validateReservation(ReservationRequest request, String time) {
		if (request.name() == null) {
			throw new RoomEscapeException(ErrorCode.INVALID_RESERVATION);
		}

		if (request.date() == null) {
			throw new RoomEscapeException(ErrorCode.INVALID_TIME);
		}

		LocalDate reservationDate = LocalDate.parse(request.date());
		LocalTime reservationTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
		if (reservationDate.isBefore(LocalDate.now()) ||
				(reservationDate.isEqual(LocalDate.now()) && reservationTime.isBefore(LocalTime.now()))) {
			throw new RoomEscapeException(ErrorCode.PAST_RESERVATION);
		}
	}

}
