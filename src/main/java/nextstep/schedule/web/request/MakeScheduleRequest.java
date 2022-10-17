package nextstep.schedule.web.request;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.schedule.domain.Schedule;

public class MakeScheduleRequest {

    private final Long themeId;
    private final String date;
    private final String time;

    public MakeScheduleRequest(Long themeId, String date, String time) {
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }

    public Schedule toSchedule() {
        return new Schedule(themeId, LocalDate.parse(date), LocalTime.parse(time));
    }

    public Long getThemeId() {
        return themeId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
