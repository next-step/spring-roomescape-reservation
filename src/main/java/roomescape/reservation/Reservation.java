package roomescape.reservation;

import roomescape.reservationTime.ReservationTime;
import roomescape.theme.Theme;

public class Reservation {
	private Long id;

	private String name;

	private String date;

	private ReservationTime reservationTime;

	private Theme theme;

	public Reservation(Long id, String name, String date, ReservationTime reservationTime, Theme theme) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.reservationTime = reservationTime;
		this.theme = theme;
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
		return reservationTime.getStartAt();
	}

	public String getThemeName() {
		return theme.getName();
	}
}
