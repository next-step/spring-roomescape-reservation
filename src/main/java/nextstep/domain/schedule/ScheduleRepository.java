package nextstep.domain.schedule;

import java.util.List;
import java.util.Optional;
import nextstep.domain.schedule.dto.ScheduleCommandDto;
import nextstep.domain.schedule.dto.ScheduleFindCondition;

public interface ScheduleRepository {

  default Schedule save(ScheduleCommandDto.Create createReq) {
    throw new UnsupportedOperationException();
  }

  /**
   * @param deleteReq
   * @return 실제로 삭제 되었는지 여부. 삭제 되었으면 true, 그렇지 않으면 false
   */
  default boolean delete(ScheduleCommandDto.Delete deleteReq) {
    throw new UnsupportedOperationException();
  }

  default List<Schedule> findAll(ScheduleFindCondition findCondition) {
    throw new UnsupportedOperationException();
  }

  default Optional<Schedule> findById(Long id) {
    throw new UnsupportedOperationException();
  }
}
