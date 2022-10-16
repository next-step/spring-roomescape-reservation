package nextstep.domain.repository;

import java.util.Optional;
import nextstep.domain.Schedule;

public interface ScheduleRepository {

    void save(Schedule schedule);

    Optional<Schedule> findByReservationId(Long reservationId);

    Optional<Schedule> findByScheduleId(Long id);

    Optional<Schedule> findBy(Long themeId, Long reservationId);

    void delete(Long id);

    void deleteAll();
}
