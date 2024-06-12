package roomescape.repository.reservation;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> findAllReservations() {
        return reservations;
    }
}
