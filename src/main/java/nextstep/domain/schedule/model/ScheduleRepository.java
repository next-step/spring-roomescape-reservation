package nextstep.domain.schedule.model;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Long create(Schedule schedule);
    Optional<Schedule> findById(Long id);
    List<Schedule> findAllByDate(String date);
    List<Schedule> findAllByThemeId(Long themeId);
    List<Schedule> findAllByThemeIdAndDate(Long themeId, String date);

    void removeById(Long id);
}
