package nextstep.application.schedule;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.schedule.dto.Schedule;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulePolicy {

  private final List<ScheduleValidation> validations;

  public void checkValid(Schedule schedule) {
    validations.forEach(it -> it.checkValid(schedule));
  }

}
