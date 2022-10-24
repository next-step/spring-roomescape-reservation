package nextstep.application.schedule;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.schedule.dto.ScheduleDeleteValidationDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleDeletePolicy {

  private final List<ScheduleDeleteValidation> validations;

  public void checkValid(ScheduleDeleteValidationDto validationDto) {
    validations.forEach(it -> it.checkValid(validationDto));
  }
}
