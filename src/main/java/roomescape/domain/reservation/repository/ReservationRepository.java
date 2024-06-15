package roomescape.domain.reservation.repository;

import roomescape.domain.reservation.domain.model.Reservation;
import roomescape.domain.reservation.domain.model.ReservationGuestName;
import roomescape.domain.reservation.domain.model.ReservationTimeStamp;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    Optional<Reservation> findById(Long reservationId);
    Optional<Reservation> findByNameAndTimestamp(ReservationGuestName name, ReservationTimeStamp timestamp);

}
