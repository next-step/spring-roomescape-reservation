package roomescape.domain.reservation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.domain.Reservation;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> fetchActiveReservations() {
        final List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                .filter(Reservation::isActive)
                .toList();
    }
}
