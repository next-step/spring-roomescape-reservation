package nextstep.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDeleteRequest {
    private final LocalDate date;
    private final LocalTime time;

    private ReservationDeleteRequest(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    public static ReservationDeleteRequest of(String date, String time) {
        return new ReservationDeleteRequest(LocalDate.parse(date), LocalTime.parse(time + ":00"));
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
