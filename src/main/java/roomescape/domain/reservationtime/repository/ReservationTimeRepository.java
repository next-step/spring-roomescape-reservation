package roomescape.domain.reservationtime.repository;

import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.domain.reservationtime.model.ReservationTimeId;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    ReservationTime getById(ReservationTimeId timeId);

    Optional<ReservationTime> findById(ReservationTimeId timeId);

    List<ReservationTime> findAll();

    void deleteAllInBatch();

    Optional<ReservationTime> findByStartAt(LocalTime startAt);

    ReservationTime getByStartAt(LocalTime startAt);

    void delete(ReservationTimeId timeId);
}
