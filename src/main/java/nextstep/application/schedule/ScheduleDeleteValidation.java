package nextstep.application.schedule;

import nextstep.application.schedule.dto.ScheduleDeleteValidationDto;

public interface ScheduleDeleteValidation {

  void checkValid(ScheduleDeleteValidationDto validationDto);

  int priority();
}
