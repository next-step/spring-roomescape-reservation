package nextstep.domain.schedule.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import nextstep.domain.schedule.ScheduleEntity;

public interface ScheduleRepository {

  ScheduleEntity save(ScheduleEntity schedule);

  Optional<ScheduleEntity> findSchedule(Long themeId, LocalDate date, LocalTime time);

  Optional<ScheduleEntity> findSchedule(Long id);

  List<ScheduleEntity> findSchedules(Long themeId, LocalDate date);

  void deleteSchedule(Long id);
}
