package roomescape.reservationTime;

import jakarta.validation.constraints.NotBlank;
import roomescape.util.DateTimeUtils;

import java.text.ParseException;

public class ReservationTimeRequest {
	@NotBlank(message = "시간")
	private String startAt;

	public ReservationTimeRequest(String startAt) throws ParseException {
		DateTimeUtils.validTimeFormat(startAt);
		this.startAt = startAt;
	}

	public ReservationTimeRequest() {
	}

	public String getStartAt() {
		return startAt;
	}
}
