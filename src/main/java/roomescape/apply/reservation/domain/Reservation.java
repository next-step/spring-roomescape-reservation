package roomescape.apply.reservation.domain;

import roomescape.apply.reservationtime.domain.ReservationTime;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private ReservationTime time;

    protected Reservation() {

    }

    public Reservation(Long id, String name, String date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(String name, String date, ReservationTime time) {
        Reservation reservation = new Reservation();
        reservation.name = name;
        reservation.date = date;
        reservation.time = time;
        return reservation;
    }

    public void changeId(long id) {
        this.id = id;
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

    public ReservationTime getTime() {
        return time;
    }
}
