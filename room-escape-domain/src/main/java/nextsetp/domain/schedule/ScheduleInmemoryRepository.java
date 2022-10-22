package nextsetp.domain.schedule;

import java.time.LocalDate;
import java.util.List;

public class ScheduleInmemoryRepository implements ScheduleRepository{
    @Override
    public Long save(Schedule schedule) {
        return null;
    }

    @Override
    public List<Schedule> findAllBy(Long themeId, LocalDate date) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
