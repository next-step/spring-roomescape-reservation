package nextstep.app.web.schedule.port.query;

import nextstep.domain.schedule.domain.model.Schedule;
import nextstep.domain.schedule.domain.model.ScheduleRepository;
import nextstep.domain.theme.domain.model.Theme;
import nextstep.domain.theme.domain.model.ThemeRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ScheduleQuery {
    private final ScheduleRepository scheduleRepository;
    private final ThemeRepository themeRepository;

    public ScheduleQuery(ScheduleRepository scheduleRepository, ThemeRepository themeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.themeRepository = themeRepository;
    }

    public Map<Schedule, Theme> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new IllegalArgumentException("아이디와 일치하는 테마가 존재하지 않습니다."));
        List<Schedule> schedules = scheduleRepository.findAllByThemeIdAndDate(themeId, LocalDate.parse(date));
        return schedules.stream()
                .collect(Collectors.toMap(Function.identity(), __ -> theme));
    }
}
