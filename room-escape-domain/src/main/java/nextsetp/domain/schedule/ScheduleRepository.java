package nextsetp.domain.schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository<T> {
    Long save(T schedule);
    List<Schedule> findAllBy(Long themeId, LocalDate date);
    void delete(Long id);
}
