package nextstep.domain.schedule.service;

import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.schedule.model.ScheduleRepository;
import nextstep.domain.theme.model.Theme;
import nextstep.domain.theme.model.ThemeRepository;
import nextstep.exception.ClientException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ThemeRepository themeRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, ThemeRepository themeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.themeRepository = themeRepository;
    }

    public Long create(Schedule schedule) {
        Optional<Theme> theme = themeRepository.findById(schedule.getThemeId());

        if (theme.isEmpty()) {
            throw new ClientException(String.format("요청한 테마(ID:%d)가 없어 일정을 생성할 수 없습니다.", schedule.getThemeId()));
        }

        return scheduleRepository.create(schedule);
    }
}
