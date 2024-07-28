package roomescape.core.domain.reservation;


import roomescape.core.domain.reservationtime.ReservationTimeId;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    Reservation getById(Long reservationId);

    Optional<Reservation> findBy(
            ReservationGuestName name,
            ReservationDate date,
            ReservationTimeId timeId
    );

    List<Reservation> findAllByTimeId(ReservationTimeId timeId);
}
