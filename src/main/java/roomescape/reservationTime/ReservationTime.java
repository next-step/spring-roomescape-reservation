package roomescape.reservationTime;

import java.time.LocalTime;

public class ReservationTime {
	private Long id;

	private LocalTime startAt;

	public ReservationTime(Long id, LocalTime startAt) {
		this.id = id;
		this.startAt = startAt;
	}

	public ReservationTime(String startAtStr) {
		this(null, LocalTime.parse(startAtStr));
	}

	public Long getId() {
		return id;
	}

	public LocalTime getStartAt() {
		return startAt;
	}
}
