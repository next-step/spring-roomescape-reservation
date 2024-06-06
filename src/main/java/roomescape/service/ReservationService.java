package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.reservation.Reservations;

@Service
public class ReservationService {

    private final Reservations reservations = Reservations.createEmpty();

    public Reservations findReservations() {
        return reservations;
    }
}
