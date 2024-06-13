package roomescape.Repository;

import roomescape.Entity.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {
    List<ReservationTime> findAll();
    ReservationTime findById(Long id);
    ReservationTime findByStartAt(String startAt);
    Long countReservationMatchWith(Long id);
    long save(String startAt);
    long cancelById(Long id);
}
