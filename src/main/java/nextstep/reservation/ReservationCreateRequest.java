package nextstep.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCreateRequest {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public ReservationCreateRequest(LocalDate date, LocalTime time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Reservation toObject() {
        return Reservation.of(this.date, this.time, this.name);
    }
}
