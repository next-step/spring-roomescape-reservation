package roomescape.domain.reservation;


import roomescape.domain.reservationtime.ReservationTimeId;
import roomescape.domain.theme.ThemeId;

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
