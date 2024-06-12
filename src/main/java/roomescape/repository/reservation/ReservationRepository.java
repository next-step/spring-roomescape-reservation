package roomescape.repository.reservation;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository {
    public List<Reservation> findAllReservations();
}
