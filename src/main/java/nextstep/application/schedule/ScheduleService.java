package nextstep.application.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.schedule.dto.Schedule;
import nextstep.application.schedule.dto.ScheduleRes;
import nextstep.domain.schedule.ScheduleEntity;
import nextstep.domain.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

  private final ScheduleRepository repository;

  public Long create(Schedule req) {
    var schedule = ScheduleEntity.builder()
        .themeId(req.themeId())
        .date(req.date())
        .time(LocalTime.parse(req.time()))
        .build();
    var entity = repository.save(schedule);
    return entity.getId();
  }

  public List<ScheduleRes> getSchedules(Long themeId, LocalDate date) {
    return null;
  }

  public void deleteSchedule(Long id) {

  }
}
