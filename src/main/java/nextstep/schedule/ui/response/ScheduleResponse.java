package nextstep.schedule.ui.response;

import static java.util.stream.Collectors.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.domain.Theme;

public class ScheduleResponse {

    private final Long id;
    private final ThemeResponse theme;
    private final LocalDate date;
    private final LocalTime time;

    public ScheduleResponse(Long id, ThemeResponse theme, LocalDate date, LocalTime time) {
        this.id = id;
        this.theme = theme;
        this.date = date;
        this.time = time;
    }

    public static ScheduleResponse from(Schedule schedule, Theme theme) {
        return new ScheduleResponse(
            schedule.getId(),
            ThemeResponse.from(theme),
            schedule.getDate(),
            schedule.getTime()
        );
    }

    public static List<ScheduleResponse> of(List<Schedule> schedules, Theme theme) {
        return schedules.stream()
            .map(it -> from(it, theme))
            .collect(toList());
    }

    public Long getId() {
        return id;
    }

    public ThemeResponse getTheme() {
        return theme;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
