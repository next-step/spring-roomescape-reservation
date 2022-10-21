package nextstep.app.web.schedule;

import nextstep.core.schedule.in.ScheduleCreateRequest;

import java.time.LocalDate;
import java.time.LocalTime;

class ScheduleCreateWebRequest {
    private Long themeId;
    private LocalDate date;
    private LocalTime time;

    private ScheduleCreateWebRequest() {
    }

    public ScheduleCreateRequest to() {
        return new ScheduleCreateRequest(themeId, date, time);
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
