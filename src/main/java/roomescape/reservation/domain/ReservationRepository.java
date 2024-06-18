package roomescape.reservation.domain;

import roomescape.reservation.domain.entity.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll();
    Reservation findById(Long id);
    Long countMatchWith(String date, Long timeId, Long themeId);
    long save(String name, String date, Long timeId, Long themeId);
    long deleteById(Long id);
}
