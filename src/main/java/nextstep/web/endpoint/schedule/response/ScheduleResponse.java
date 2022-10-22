
package nextstep.web.endpoint.schedule.response;

import lombok.Getter;
import lombok.ToString;
import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.theme.model.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class ScheduleResponse {
    private final Long id;
    private final ScheduleThemeResponse theme;
    private final LocalDate date;
    private final LocalTime time;

    public ScheduleResponse(Long id, ScheduleThemeResponse theme, LocalDate date, LocalTime time) {
        this.id = id;
        this.theme = theme;
        this.date = date;
        this.time = time;
    }

    public static List<ScheduleResponse> fromList(List<Schedule> schedules, Theme theme) {
        return schedules.stream()
                .map(schedule -> new ScheduleResponse(
                        schedule.getId(),
                        ScheduleThemeResponse.from(theme),
                        schedule.getDate(),
                        schedule.getTime())
                ).collect(Collectors.toList());
    }
}
