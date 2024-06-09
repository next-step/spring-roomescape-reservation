package roomescape.domain.reservation.repository;

import roomescape.domain.reservation.domain.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Long save(Reservation reservation);

    List<Reservation> findAll();

    Optional<Reservation> findById(Long reservationId);

}
