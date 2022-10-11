package nextstep.reservation.controller.request;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public class RemoveReservationRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    private final LocalTime time;

    public RemoveReservationRequest(LocalDate date, LocalTime time) {
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
