package nextstep.app.web.schedule.port.web;

import nextstep.app.web.theme.port.web.ThemeResponse;
import nextstep.domain.schedule.domain.model.Schedule;
import nextstep.domain.theme.domain.model.Theme;

import java.util.List;
import java.util.Map;

public record ScheduleResponse(Long id,
                               ThemeResponse theme,
                               String date,
                               String time) {
    public static List<ScheduleResponse> listOf(Map<Schedule, Theme> scheduleAndThemes) {
        return scheduleAndThemes.entrySet().stream()
                .map(ScheduleResponse::from)
                .toList();
    }

    private static ScheduleResponse from(Map.Entry<Schedule, Theme> scheduleAndTheme) {
        Schedule schedule = scheduleAndTheme.getKey();
        return new ScheduleResponse(
                schedule.id(),
                ThemeResponse.from(scheduleAndTheme.getValue()),
                schedule.date().toString(),
                schedule.time().toString()
        );
    }
}
