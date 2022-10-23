package nextstep.web.schedule.dto;

import nextstep.domain.schedule.service.ScheduleResponse;

public class ScheduleWebResponse {
    public Long id;
    public ThemeWebDto theme;
    public String date;
    public String time;

    public ScheduleWebResponse(ScheduleResponse response) {
        this.id = response.getId();
        this.theme = new ThemeWebDto(response.getTheme());
        this.date = response.getDate();
        this.time = response.getTime();
    }
}
