package roomescape.reservation;

import roomescape.reservationTime.ReservationTime;

import java.util.Objects;

public class Reservation {

    private final static ReservationPolicy reservationPolicy = new ReservationPolicy();

    private Long id;
    private String name;
    private String date;
    private ReservationTime reservationTime;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public Reservation(String name, String date, ReservationTime reservationTime) {
        this(null, name, date, reservationTime);
    }

    public Reservation(Long id, String name, String date, ReservationTime reservationTime) {
        this.id = id;
        if(reservationPolicy.validateName(name)) {
            throw new IllegalArgumentException("예약자 이름에 특수문자가 포함되어 있습니다.");
        }
        this.name = name;

        if(reservationPolicy.validateDate(date)) {
            throw new IllegalArgumentException("예약 날짜 형식이 올바르지 않습니다.");
        }
        this.date = date;
        this.reservationTime = reservationTime;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static class Builder {
        private Long id;
        private String name;
        private String date;
        private ReservationTime reservationTime;


        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder reservationTime(ReservationTime reservationTime) {
            this.reservationTime = reservationTime;
            return this;
        }

        public Reservation build() {
            return new Reservation(id, name, date, reservationTime);
        }
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", reservationTime='" + reservationTime + '\'' +
                '}';
    }

}
