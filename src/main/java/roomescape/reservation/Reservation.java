package roomescape.reservation;

public class Reservation {
	private Long id;

	private String name;

	private String date;

	private String time;

	public Reservation(Long id, String name, String date, String time) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.time = time;
	}

	public ReservationResponse convert() {
		return new ReservationResponse(id, name, date, time);
	}
}
