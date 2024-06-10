package roomescape.repository;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.reservation.create.ReservationCreateRequest;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findReservations();

    Reservation createReservation(ReservationCreateRequest dto, ReservationTime time);

//    ReservationDto findReservationById(Long id);

    void deleteReservation(Long id);
}
