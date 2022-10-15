package nextstep.schedule.ui.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleCreateRequest {

    private Long themeId;
    private LocalDate date;
    private LocalTime time;

    protected ScheduleCreateRequest() {
    }

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
