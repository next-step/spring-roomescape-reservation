package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface ReservationTimeDao {

    ReservationTime save(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    void delete(Long id);

    Optional<ReservationTime> findById(Long id);
}
