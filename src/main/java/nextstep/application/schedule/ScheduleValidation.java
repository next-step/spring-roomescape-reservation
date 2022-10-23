package nextstep.application.schedule;

import nextstep.application.schedule.dto.Schedule;

public interface ScheduleValidation {

  void checkValid(Schedule schedule);

  int priority();
}
