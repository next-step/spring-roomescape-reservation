package nextstep.domain.schedule.service;

import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.schedule.model.ScheduleRepository;
import nextstep.domain.theme.model.Theme;
import nextstep.domain.theme.model.ThemeRepository;
import nextstep.exception.ClientException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<ScheduleResponse> findAllByThemeIdAndDate(Long themeId, String date) {
        Optional<Theme> theme = themeRepository.findById(themeId);

        if (theme.isEmpty()) {
            return Collections.emptyList();
        }

        return scheduleRepository.findAllByThemeIdAndDate(themeId, date).stream()
            .map(it -> new ScheduleResponse(it, theme.get()))
            .collect(Collectors.toList());
    }

    public List<ScheduleResponse> findAllByDate(String date) {
        return scheduleRepository.findAllByDate(date).stream()
            .map(schedule -> {
                Theme theme = themeRepository.findById(schedule.getThemeId()).orElseThrow(() -> new ClientException(String.format("조회한 테마(ID:%d)가 존재하지 않습니다.", schedule.getThemeId())));
                return new ScheduleResponse(schedule, theme);
            })
            .collect(Collectors.toList());
    }
}
