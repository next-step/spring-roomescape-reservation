package nextstep.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleCreateRequest {

    private final long themeId;
    private final LocalDate date;
    private final LocalTime time;

    public ScheduleCreateRequest(long themeId, LocalDate date, LocalTime time) {
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }

    public long getThemeId() {
        return themeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Schedule toObject() {
        return Schedule.of(this.themeId, this.date, this.time);
    }
}
