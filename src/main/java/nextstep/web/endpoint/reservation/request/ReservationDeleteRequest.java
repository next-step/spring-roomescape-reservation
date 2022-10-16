package nextstep.web.endpoint.reservation.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDeleteRequest {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    private final LocalTime time;

    public ReservationDeleteRequest(LocalDate date, LocalTime time, String name) {
        this.date = date;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
