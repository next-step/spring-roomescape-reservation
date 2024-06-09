package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTheme;

public interface ReservationThemeDao {

    ReservationTheme save(ReservationTheme reservationTheme);

    List<ReservationTheme> findAll();

    void delete(Long id);
}
