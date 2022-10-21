package nextstep.core.schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {
    Schedule save(Schedule schedule);

    List<Schedule> findByThemeIdAndDate(Long themeId, LocalDate date);
}
