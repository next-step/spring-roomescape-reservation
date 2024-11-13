package roomescape.domain.reservationtime.domain;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    ReservationTime getById(ReservationTimeId timeId);

    List<ReservationTime> findAll();

    List<ReservationTime> findAllByIds(List<ReservationTimeId> reservationTimeIds);

    Optional<ReservationTime> findByStartAt(LocalTime startAt);

    ReservationTime getByStartAt(LocalTime startAt);

    void delete(ReservationTimeId timeId);

    void delete(ReservationTime reservationTime);
}
