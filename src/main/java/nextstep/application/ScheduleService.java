package nextstep.application;

import java.time.LocalDate;
import java.util.List;
import nextstep.domain.Schedule;
import nextstep.domain.ScheduleRepository;
import nextstep.domain.Theme;
import nextstep.domain.ThemeRepository;
import nextstep.ui.request.ScheduleCreateRequest;
import nextstep.ui.response.ScheduleResponse;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ThemeRepository themeRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, ThemeRepository themeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.themeRepository = themeRepository;
    }

    public ScheduleResponse create(ScheduleCreateRequest request) {
        Theme theme = themeRepository.findById(request.getThemeId());
        Schedule schedule = scheduleRepository.save(new Schedule(
            theme.getId(),
            request.getDate(),
            request.getTime()
        ));
        return ScheduleResponse.from(schedule, theme);
    }

    public List<ScheduleResponse> findAllByThemeAndDate(Long themeId, LocalDate date) {
        Theme theme = themeRepository.findById(themeId);
        return ScheduleResponse.of(scheduleRepository.findAllByThemeIdAndDate(themeId, date), theme);
    }

    public void deleteById(Long scheduleId) {
        int deletedCount = scheduleRepository.deleteById(scheduleId);
        if (deletedCount == 0) {
            throw new IllegalArgumentException("ID 에 해당하는 스케줄이 없습니다.");
        }
    }
}
