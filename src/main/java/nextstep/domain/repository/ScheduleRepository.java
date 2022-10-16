package nextstep.domain.repository;

import java.util.List;
import java.util.Optional;
import nextstep.domain.Schedule;

public interface ScheduleRepository {

    void save(Schedule schedule);

    Optional<Schedule> findBy(Long reservationId);

    Optional<Schedule> findBy(Long themeId, Long reservationId);

    void delete(Long id);

    void deleteAll();
}
