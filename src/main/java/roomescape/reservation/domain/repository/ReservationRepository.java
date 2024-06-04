package roomescape.reservation.domain.repository;

import java.util.List;

import roomescape.reservation.domain.Reservation;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();
}
