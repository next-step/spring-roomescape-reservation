package roomescape.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.repository.ReservationRepository;

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
