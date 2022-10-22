package nextstep.domain.schedule.repository;

import nextstep.domain.schedule.ScheduleEntity;

public interface ScheduleRepository {

  ScheduleEntity save(ScheduleEntity schedule);
}
