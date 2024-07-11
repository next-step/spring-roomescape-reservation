package roomescape.domain.reservationtime.application;

import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeId;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    ReservationTime getById(ReservationTimeId timeId);

    List<ReservationTime> findAll();

    Optional<ReservationTime> findByStartAt(LocalTime startAt);

    ReservationTime getByStartAt(LocalTime startAt);

    void delete(ReservationTimeId timeId);
}
