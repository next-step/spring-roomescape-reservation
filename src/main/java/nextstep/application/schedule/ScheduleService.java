package nextstep.application.schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nextstep.application.schedule.dto.Schedule;
import nextstep.application.schedule.dto.ScheduleRes;
import nextstep.application.themes.ThemeService;
import nextstep.domain.schedule.ScheduleEntity;
import nextstep.domain.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

  private final ThemeService themeService;

  private final ScheduleRepository repository;

  private final ScheduleCreatePolicy policy;

  @Transactional
  public Long create(Schedule req) {
    policy.checkValid(req);
    var schedule = ScheduleEntity.builder()
        .themeId(req.themeId())
        .date(req.date())
        .time(req.time())
        .build();
    var entity = repository.save(schedule);
    return entity.getId();
  }

  public List<ScheduleRes> getSchedules(Long themeId, LocalDate date) {
    var schedules = repository.findSchedules(themeId, date);
    return schedules.stream()
        .map(it -> ScheduleRes.builder()
            .id(it.getId())
            .theme(themeService.getTheme(themeId).orElseThrow(() -> new IllegalArgumentException("테마를 찾을 수 없습니다")))
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
              .theme(themeService.getTheme(schedule.getThemeId())
                  .orElseThrow(() -> new IllegalArgumentException("테마를 찾을 수 없습니다")))
              .date(schedule.getDate())
              .time(schedule.getTime())
              .build()
      );
    }
    return Optional.empty();
  }

  public void deleteSchedule(Long id) {
    repository.deleteSchedule(id);
  }
}
