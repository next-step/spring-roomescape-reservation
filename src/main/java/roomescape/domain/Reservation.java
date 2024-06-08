package roomescape.domain;

public class Reservation {

	private Long id;

	private String name;

	private String date;

	private ReservationTime time;

	public static Builder builder() {
		return new Builder();
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ReservationTime getTime() {
		return this.time;
	}

	public void setTime(ReservationTime time) {
		this.time = time;
	}

	public static final class Builder {

		private final Reservation reservation;

		public Builder() {
			this.reservation = new Reservation();
		}

		public Builder id(long id) {
			this.reservation.id = id;
			return this;
		}

		public Builder name(String name) {
			this.reservation.name = name;
			return this;
		}

		public Builder date(String date) {
			this.reservation.date = date;
			return this;
		}

		public Builder time(ReservationTime time) {
			this.reservation.time = time;
			return this;
		}

		public Reservation build() {
			return this.reservation;
		}

	}

}
