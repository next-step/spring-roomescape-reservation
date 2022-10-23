package nextstep.application.schedule;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.schedule.dto.Schedule;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleCreatePolicy {

  private final List<ScheduleCreateValidation> validations;

  public void checkValid(Schedule schedule) {
    validations.forEach(it -> it.checkValid(schedule));
  }

}
