package nextstep.application.schedule;

import lombok.RequiredArgsConstructor;
import nextstep.application.schedule.dto.Schedule;
import nextstep.domain.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleCreateValidation implements ScheduleValidation {

  private final ScheduleRepository repository;

  @Override
  public void checkValid(Schedule schedule) {
    var entity = repository.findSchedule(schedule.themeId(), schedule.date(), schedule.time());
    if (entity.isPresent()) {
      throw new IllegalArgumentException(String.format("이미 동일한 시각에 동일한 테마가 설정되어 있습니다. 테마ID: %s, 날짜: %s, 시간 %s",
          schedule.themeId(), schedule.date(), schedule.time()));
    }
  }

  @Override
  public int priority() {
    return 1;
  }
}
