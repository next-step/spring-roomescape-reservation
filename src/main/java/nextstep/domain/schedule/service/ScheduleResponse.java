package nextstep.domain.schedule.service;

import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.theme.model.Theme;

public class ScheduleResponse {
    private Long id;
    private ThemeDto theme;
    private String date;
    private String time;

    public ScheduleResponse(Schedule schedule, Theme theme) {
        this.id = schedule.getId();
        this.theme = new ThemeDto(theme);
        this.date = schedule.getDate().toString();
        this.time = schedule.getTime().toString();
    }

    public Long getId() {
        return id;
    }

    public ThemeDto getTheme() {
        return theme;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
