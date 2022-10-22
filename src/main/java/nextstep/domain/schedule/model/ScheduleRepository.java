package nextstep.domain.schedule.model;

import java.util.List;

public interface ScheduleRepository {
    Long create(Schedule schedule);

    List<Schedule> findAllByThemeIdAndDate(Long themeId, String date);
}
