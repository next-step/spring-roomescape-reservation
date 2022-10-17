package nextstep.domain.schedule;

import java.util.List;
import nextstep.domain.schedule.dto.ScheduleCommandDto;

public interface ScheduleRepository {

  default Schedule save(ScheduleCommandDto.Create createReq) {
    throw new UnsupportedOperationException();
  }

  default boolean delete(ScheduleCommandDto.Delete deleteReq) {
    throw new UnsupportedOperationException();
  }

  default List<Schedule> findAll() {
    throw new UnsupportedOperationException();
  }
}
