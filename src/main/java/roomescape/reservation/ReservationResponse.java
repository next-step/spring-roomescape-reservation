package roomescape.reservation;

public class ReservationResponse {
	private Long id;

	private String name;

	private String date;

	private String time;

	public ReservationResponse(Long id, String name, String date, String time) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.time = time;
	}

	public ReservationResponse(Reservation reservation) {
		this(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}
}
