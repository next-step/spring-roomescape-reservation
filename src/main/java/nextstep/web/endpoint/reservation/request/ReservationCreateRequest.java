package nextstep.web.endpoint.reservation.request;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
public class ReservationCreateRequest {
    private final Long scheduleId;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public ReservationCreateRequest(Long scheduleId, LocalDate date, LocalTime time, String name) {
        this.scheduleId = scheduleId;
        this.date = date;
        this.time = time;
        this.name = name;
    }
}
