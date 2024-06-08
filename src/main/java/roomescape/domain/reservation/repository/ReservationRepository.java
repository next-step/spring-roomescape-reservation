package roomescape.domain.reservation.repository;

import roomescape.domain.reservation.domain.model.Reservation;

import java.util.List;

public interface ReservationRepository {

    Long save(Reservation reservation);

    List<Reservation> findAll();

}
