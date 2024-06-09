package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationDao {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    void delete(Long id);

    Long countByDateAndTimeId(String date, String timeId);

    Long countByTimeId(Long timeId);

    Long countByThemeId(Long themeId);
}
