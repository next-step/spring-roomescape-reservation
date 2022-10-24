package nextstep.application.schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nextstep.application.schedule.dto.Schedule;
import nextstep.application.schedule.dto.ScheduleDeleteValidationDto;
import nextstep.application.schedule.dto.ScheduleRes;
import nextstep.domain.schedule.ScheduleEntity;
import nextstep.domain.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

  private final ScheduleQueryService scheduleQueryService;

  private final ScheduleRepository repository;

  private final ScheduleCreatePolicy createPolicy;
  private final ScheduleDeletePolicy deletePolicy;

  @Transactional
  public Long create(Schedule req) {
    createPolicy.checkValid(req);
    var schedule = ScheduleEntity.builder()
        .themeId(req.themeId())
        .date(req.date())
        .time(req.time())
        .build();
    var entity = repository.save(schedule);
    return entity.getId();
  }

  public List<ScheduleRes> getSchedules(Long themeId, LocalDate date) {
    return scheduleQueryService.getSchedules(themeId, date);
  }

  public Optional<ScheduleRes> getSchedule(Long scheduleId) {
    return scheduleQueryService.getSchedule(scheduleId);
  }

  public void deleteSchedule(Long id) {
    deletePolicy.checkValid(ScheduleDeleteValidationDto.builder()
        .id(id)
        .build());
    repository.deleteSchedule(id);
  }
}
