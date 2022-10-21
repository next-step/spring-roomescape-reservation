package nextstep.core.schedule;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Schedule create(Schedule schedule) {
        return repository.save(schedule);
    }

    @Transactional(readOnly = true)
    public List<Schedule> list(Long themeId, LocalDate date) {
        return repository.findByThemeIdAndDate(themeId, date);
    }
}
