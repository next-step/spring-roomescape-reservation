package nextstep.web.endpoint.reservation.request;

import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@ToString
public class ReservationsSearchRequest {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    public ReservationsSearchRequest(LocalDate date) {
        this.date = date;
    }
}
