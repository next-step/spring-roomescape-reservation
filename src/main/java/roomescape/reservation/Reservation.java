package roomescape.reservation;

import roomescape.reservationTime.ReservationTime;

public class Reservation {
	private Long id;

	private String name;

	private String date;

	private ReservationTime reservationTime;

	public Reservation(Long id, String name, String date, ReservationTime reservationTime) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.reservationTime = reservationTime;
	}

	public ReservationResponse convert() {
		return new ReservationResponse(id, name, date, reservationTime.getStartAt());
	}
}
