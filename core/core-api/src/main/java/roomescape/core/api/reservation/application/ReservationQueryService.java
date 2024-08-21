package roomescape.core.api.reservation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.core.domain.reservation.Reservation;
import roomescape.core.domain.reservation.ReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> fetchActiveReservations() {
        return reservationRepository.findNotDeletedReservations();
    }
}
