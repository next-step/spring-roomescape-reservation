package nextstep.reservation.web.request;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.reservation.domain.Reservation;

public class MakeReservationRequest {

    private final String date;
    private final String time;
    private final String name;

    public MakeReservationRequest(String date, String time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public Reservation toReservation() {
        return new Reservation(LocalDate.parse(date), LocalTime.parse(time), name);
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
