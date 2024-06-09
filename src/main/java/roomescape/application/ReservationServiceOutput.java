package roomescape.application;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

public class ReservationServiceOutput {

    private final long id;
    private final String name;
    private final String date;
    private final Time time;

    private ReservationServiceOutput(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = reservation.getTime();
    }

    public static ReservationServiceOutput createReservationServiceOutput(Reservation reservation) {
        return new ReservationServiceOutput(reservation);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

}
