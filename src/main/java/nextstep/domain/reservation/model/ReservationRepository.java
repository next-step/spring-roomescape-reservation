package nextstep.domain.reservation.model;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Long create(Reservation reservation);
    Optional<Reservation> findByScheduleId(Long scheduleId);
    List<Reservation> findAllByScheduledIds(List<Long> scheduleIds);
    void removeByScheduleId(Long scheduleId);
}
