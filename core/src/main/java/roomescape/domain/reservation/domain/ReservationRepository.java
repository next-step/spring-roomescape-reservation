package roomescape.domain.reservation.domain;


import roomescape.domain.reservationtime.domain.ReservationTimeId;
import roomescape.domain.theme.domain.ThemeId;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Reservation getById(Long reservationId);

    Optional<Reservation> findBy(
            ReservationGuestName name,
            ReservationDate date,
            ReservationTimeId timeId
    );

    List<Reservation> findAllByTimeId(ReservationTimeId timeId);

    List<Reservation> findAllByThemeId(ThemeId themeId);

    List<Reservation> findAll();
}
