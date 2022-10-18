package nextsetp.domain.schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {
    Long save(Schedule schedule);
    List<Schedule> findAllBy(Long themeId, LocalDate date);
    void delete(Long id);
}
