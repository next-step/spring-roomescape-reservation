package nextstep.reservation.controller.request;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class ReservationListRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    public ReservationListRequest(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
