package nextstep.domain;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {

    Schedule save(Schedule schedule);

    List<Schedule> findAllByThemeIdAndDate(Long themeId, LocalDate date);

    int deleteById(Long id);
}
