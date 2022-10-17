package nextstep.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final ReservationTime reservationTime;
    private final String name;

    public Reservation(LocalDate date, LocalTime time, String name) {
        this.reservationTime = new ReservationTime(date, time);
        this.name = name;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public LocalDate getDate() {
        return reservationTime.getDate();
    }

    public LocalTime getTime() {
        return reservationTime.getTime();
    }

    public String getName() {
        return name;
    }
}
