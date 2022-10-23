package nextstep.domain.schedule.validator;

import nextstep.domain.schedule.dto.ScheduleCommandDto;

public interface ScheduleCreateValidator {

  default void validate(ScheduleCommandDto.Create createReq) {
    throw new UnsupportedOperationException();
  }

}
