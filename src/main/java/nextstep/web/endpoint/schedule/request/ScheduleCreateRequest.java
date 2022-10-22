package nextstep.web.endpoint.schedule.request;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
public class ScheduleCreateRequest {
    private final Long themeId;
    private final LocalDate date;
    private final LocalTime time;

    public ScheduleCreateRequest(Long themeId, LocalDate date, LocalTime time) {
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }
}
