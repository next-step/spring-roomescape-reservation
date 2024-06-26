package roomescape.domain.reservationtime;

import java.time.LocalTime;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    ReservationTime findById(Long timeId);

    boolean existByStartAt(LocalTime startAt);

    ReservationTimes findAll();

    void delete(Long timeId);
}
