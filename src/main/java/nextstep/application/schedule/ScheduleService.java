package nextstep.application.schedule;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.schedule.dto.Schedule;
import nextstep.application.schedule.dto.ScheduleRes;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

  public Long create(Schedule schedule) {
    return null;
  }

  public List<ScheduleRes> getSchedules(Long themeId, LocalDate date) {
    return null;
  }

  public void deleteSchedule(Long id) {

  }
}
