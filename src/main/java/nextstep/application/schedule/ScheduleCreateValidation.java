package nextstep.application.schedule;

import nextstep.application.schedule.dto.Schedule;

public interface ScheduleCreateValidation {

  void checkValid(Schedule schedule);

  int priority();
}
