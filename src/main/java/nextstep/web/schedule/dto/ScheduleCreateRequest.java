package nextstep.web.schedule.dto;

import nextstep.domain.schedule.model.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleCreateRequest {
    public Long themeId;
    public String date;
    public String time;

    public ScheduleCreateRequest(Long themeId, String date, String time) {
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }

    public Schedule toEntity() {
        return new Schedule(null, themeId, LocalDate.parse(date), LocalTime.parse(time));
    }
}
