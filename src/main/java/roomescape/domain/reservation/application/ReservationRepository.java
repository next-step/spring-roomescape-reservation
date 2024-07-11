package roomescape.domain.reservation.application;

import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.domain.ReservationDate;
import roomescape.domain.reservation.domain.ReservationGuestName;
import roomescape.domain.reservationtime.domain.ReservationTimeId;

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
