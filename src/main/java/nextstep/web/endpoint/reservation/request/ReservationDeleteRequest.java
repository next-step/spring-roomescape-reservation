package nextstep.web.endpoint.reservation.request;

import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
public class ReservationDeleteRequest {
    private final Long scheduleId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    private final LocalTime time;

    public ReservationDeleteRequest(Long scheduleId, LocalDate date, LocalTime time) {
        this.scheduleId = scheduleId;
        this.date = date;
        this.time = time;
    }
}
