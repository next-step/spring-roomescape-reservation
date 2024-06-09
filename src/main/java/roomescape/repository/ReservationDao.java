package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.Reservation;

public interface ReservationDao {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    void delete(Long id);

    Optional<Reservation> findByDateAndTimeStartAt(String date, String startAt);

    Optional<Reservation> findByTimeId(Long timeId);

    Optional<Reservation> findByThemeId(Long themeId);
}
