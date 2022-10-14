package nextstep.schedules.controller.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleCreateRequest {

    private final Long themeId;
    private final LocalDate date;
    private final LocalTime time;

    public ScheduleCreateRequest(Long themeId, LocalDate date, LocalTime time) {
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }

    public Long getThemeId() {
        return themeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
