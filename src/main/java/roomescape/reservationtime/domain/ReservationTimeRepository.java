package roomescape.reservationtime.domain;

import roomescape.reservationtime.domain.entity.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {
    List<ReservationTime> findAll();
    ReservationTime findById(Long id);
    ReservationTime findByStartAt(String startAt);
    Long countReservationMatchWith(Long id);
    long save(String startAt);
    long deleteById(Long id);
}
