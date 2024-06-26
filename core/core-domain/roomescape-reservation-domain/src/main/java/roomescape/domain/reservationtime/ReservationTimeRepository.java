package roomescape.domain.reservationtime;

import java.time.LocalTime;
import java.util.Optional;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    Optional<ReservationTime> findById(Long timeId);

    boolean existByStartAt(LocalTime startAt);

    ReservationTimes findAll();

    void delete(Long timeId);
}
