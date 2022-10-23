package nextstep.domain.schedule.validator;

import nextstep.domain.schedule.dto.ScheduleCommandDto;

public interface ScheduleDeleteValidator {

  default void validate(ScheduleCommandDto.Delete deleteReq) {
    throw new UnsupportedOperationException();
  }
}
