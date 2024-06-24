package roomescape.domain.reservation.repository;

import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.model.ReservationDate;
import roomescape.domain.reservation.model.ReservationGuestName;
import roomescape.domain.reservationtime.model.ReservationTimeId;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    Optional<Reservation> findById(Long reservationId);

    Optional<Reservation> findBy(
            ReservationGuestName name,
            ReservationDate date,
            ReservationTimeId timeId
    );

    void deleteAllInBatch();

    List<Reservation> findAllByTimeId(ReservationTimeId timeId);
}
