package nextstep.schedule.application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.domain.ScheduleRepository;
import nextstep.schedule.domain.Theme;
import nextstep.schedule.domain.ThemeRepository;
import nextstep.schedule.ui.request.ScheduleCreateRequest;
import nextstep.schedule.ui.response.ScheduleResponse;
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
        validateDuplicateDateTime(request.getThemeId(), request.getDate(), request.getTime());
        Theme theme = themeRepository.findById(request.getThemeId());
        Schedule schedule = scheduleRepository.save(new Schedule(
            theme.getId(),
            request.getDate(),
            request.getTime()
        ));
        return ScheduleResponse.from(schedule, theme);
    }

    private void validateDuplicateDateTime(Long themeId, LocalDate date, LocalTime time) {
        if (scheduleRepository.existsByThemeIdAndDateAndTime(themeId, date, time)) {
            throw new IllegalArgumentException("테마에 날짜와 시간이 똑같은 스케줄이 이미 존재합니다.");
        }
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
