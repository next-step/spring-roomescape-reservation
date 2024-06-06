package roomescape.apply.time.domain.repository;

import roomescape.apply.time.domain.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservation);

    List<ReservationTime> findAll();

    void delete(Long id);

    Optional<Long> checkIdExists(long id);

    Optional<ReservationTime> findById(long timeId);
}
