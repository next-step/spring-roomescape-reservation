package roomescape.apply.reservation.domain.repository;

import roomescape.apply.reservation.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    Optional<Long> checkIdExists(long id);

    void deleteById(long id);

    Optional<Long> findIdByTimeIdAndThemeId(long timeId, long themeId);

    Optional<Long> findAnyByTimeId(long id);

    Optional<Long> findAnyByThemeId(long themeId);
}
