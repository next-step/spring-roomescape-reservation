package roomescape.Repository;

import roomescape.Entity.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll();
    Reservation findById(Long id);
    long save(String name, String date, Long timeId, Long themeId);
    long deleteById(Long id);
}