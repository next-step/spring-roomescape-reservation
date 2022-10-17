package nextstep.roomescape.web.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.roomescape.core.domain.Schedule;
import nextstep.roomescape.core.domain.Theme;

public class ScheduleResponse {

    private final Integer id;
    private final ThemeResponse themeResponse;
    private final LocalDate date;
    private final LocalTime time;

    public ScheduleResponse(Integer id, ThemeResponse themeResponse, LocalDate date, LocalTime time) {
        this.id = id;
        this.themeResponse = themeResponse;
        this.date = date;
        this.time = time;
    }

    public static ScheduleResponse of(Theme theme, Schedule schedule) {
        return new ScheduleResponse(schedule.id(), ThemeResponse.of(theme), schedule.date(), schedule.time());
    }

    public Integer getId() {
        return id;
    }

    public ThemeResponse getThemeResponse() {
        return themeResponse;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
