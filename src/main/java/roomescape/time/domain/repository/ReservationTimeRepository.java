package roomescape.time.domain.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import roomescape.time.domain.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    Optional<ReservationTime> findById(Long reservationTimeId);

    List<ReservationTime> findAll();

    boolean existsByStartAt(LocalTime startAt);

    void deleteById(Long reservationTimeId);
}
