package nextstep.domain.schedule.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Schedule save(Schedule schedule);

    List<Schedule> findAllByThemeIdAndDate(Long themeId, LocalDate date);

    void deleteById(Long id);

    void deleteAll();

    Optional<Schedule> findByThemeIdAndDateTime(Long themeId, LocalDateTime dateTime);

    Optional<Schedule> findById(Long id);
}
