package nextstep.web.endpoint.reservation.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationsSearchRequest {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    public ReservationsSearchRequest(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
