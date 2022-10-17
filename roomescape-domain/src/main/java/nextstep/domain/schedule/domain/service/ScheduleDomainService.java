package nextstep.domain.schedule.domain.service;

import nextstep.domain.schedule.domain.model.Schedule;
import nextstep.domain.schedule.domain.model.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduleDomainService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleDomainService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Long create(Long themeId, LocalDateTime dateTime) {
        Schedule schedule = new Schedule(null, themeId, dateTime.toLocalDate(), dateTime.toLocalTime());

        if (scheduleRepository.findByThemeIdAndDateTime(themeId, dateTime).isPresent()) {
            throw new IllegalArgumentException("해당 테마에 동일한 시간의 스케줄이 존재합니다.");
        }

        Schedule saved = scheduleRepository.save(schedule);
        return saved.id();
    }

    public void deleteById(Long id) {
        scheduleRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return scheduleRepository.findById(id).isPresent();
    }
}
