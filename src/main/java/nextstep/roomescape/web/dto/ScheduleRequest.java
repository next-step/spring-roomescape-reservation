package nextstep.roomescape.web.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.roomescape.core.domain.Schedule;

public class ScheduleRequest {

    private final Integer id;
    private final Integer themeId;
    private final LocalDate date;
    private final LocalTime time;

    public ScheduleRequest(Integer id, Integer themeId, LocalDate date, LocalTime time) {
        this.id = id;
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }

    public Schedule toEntity() {
        return new Schedule(id, themeId, date, time);
    }

    public Integer getId() {
        return id;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
