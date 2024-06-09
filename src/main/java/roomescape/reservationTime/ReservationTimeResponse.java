package roomescape.reservationTime;

public class ReservationTimeResponse {
	private Long id;

	private String startAt;

	public ReservationTimeResponse(Long id, String startAt) {
		this.id = id;
		this.startAt = startAt;
	}

	public ReservationTimeResponse(ReservationTime reservationTime) {
		this(reservationTime.getId(), reservationTime.getStartAt());
	}

	public Long getId() {
		return id;
	}

	public String getStartAt() {
		return startAt;
	}
}
