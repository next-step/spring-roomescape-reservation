package roomescape.reservationTime;

public class ReservationTime {
	private Long id;

	private String startAt;

	public ReservationTime(Long id, String startAt) {
		this.id = id;
		this.startAt = startAt;
	}

	public Long getId() {
		return id;
	}

	public String getStartAt() {
		return startAt;
	}
}
