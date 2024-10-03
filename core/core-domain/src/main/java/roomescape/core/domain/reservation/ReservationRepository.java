package roomescape.core.domain.reservation;


import roomescape.core.domain.reservationtime.ReservationTimeId;
import roomescape.core.domain.theme.ThemeId;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findNotDeletedReservations();

    Reservation getById(Long reservationId);

    Optional<Reservation> findBy(
            ReservationGuestName name,
            ReservationDate date,
            ReservationTimeId timeId
    );

    List<Reservation> findAllByTimeId(ReservationTimeId timeId);

    List<Reservation> findAllByThemeId(ThemeId themeId);
}
