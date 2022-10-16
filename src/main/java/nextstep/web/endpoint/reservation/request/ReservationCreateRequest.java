package nextstep.web.endpoint.reservation.request;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
public class ReservationCreateRequest {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public ReservationCreateRequest(LocalDate date, LocalTime time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }
}
