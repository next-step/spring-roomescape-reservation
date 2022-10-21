package nextstep.domain.schedule.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Schedule save(Schedule schedule);

    Optional<Schedule> findById(Long id);

    List<Schedule> findAllByThemeIdAndDate(Long themeId, LocalDate date);

    Boolean existByThemId(Long themeId);

    void deleteById(Long id);
}
