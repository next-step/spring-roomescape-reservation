package nextstep.application.schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nextstep.application.schedule.dto.ScheduleRes;
import nextstep.application.themes.ThemeQueryService;
import nextstep.domain.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleQueryService {

  private final ThemeQueryService themeQueryService;

  private final ScheduleRepository repository;


  public List<ScheduleRes> getSchedules(Long themeId, LocalDate date) {
    var schedules = repository.findSchedules(themeId, date);
    return schedules.stream()
        .map(it -> ScheduleRes.builder()
            .id(it.getId())
            .theme(themeQueryService.getTheme(themeId).orElseThrow(() -> new IllegalArgumentException("테마를 찾을 수 없습니다")))
            .date(it.getDate())
            .time(it.getTime())
            .build())
        .toList();
  }

  public Optional<ScheduleRes> getSchedule(Long scheduleId) {
    var entity = repository.findSchedule(scheduleId);
    if (entity.isPresent()) {
      var schedule = entity.get();
      return Optional.ofNullable(
          ScheduleRes.builder()
              .id(schedule.getId())
              .theme(themeQueryService.getTheme(schedule.getThemeId())
                  .orElseThrow(() -> new IllegalArgumentException("테마를 찾을 수 없습니다")))
              .date(schedule.getDate())
              .time(schedule.getTime())
              .build()
      );
    }
    return Optional.empty();
  }
}
