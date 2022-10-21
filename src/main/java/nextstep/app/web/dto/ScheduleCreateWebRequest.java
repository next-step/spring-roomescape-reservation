package nextstep.app.web.dto;

import nextstep.core.schedule.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleCreateWebRequest {
    private Long themeId;
    private LocalDate date;
    private LocalTime time;

    private ScheduleCreateWebRequest() {
    }

    public Schedule to() {
        return new Schedule(themeId, date, time);
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
