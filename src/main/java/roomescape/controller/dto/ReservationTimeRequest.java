package roomescape.controller.dto;

import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;

import org.springframework.util.ObjectUtils;

public record ReservationTimeRequest(String startAt) {

	public static void validateReservationTime(ReservationTimeRequest request) {
		if (ObjectUtils.isEmpty(request.startAt())) {
			throw new RoomEscapeException(ErrorCode.INVALID_TIME);
		}
	}
}
