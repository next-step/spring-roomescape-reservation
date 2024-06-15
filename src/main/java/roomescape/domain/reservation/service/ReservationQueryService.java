package roomescape.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.domain.model.Reservation;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.reservation.service.response.ReservationQueryResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;

    public List<ReservationQueryResponse> findAll() {
        final List<Reservation> reservations = reservationRepository.findAll();

        final List<Reservation> confirmedReservations = reservations.stream()
                .filter(Reservation::isActive)
                .toList();

        return ReservationQueryResponse.from(confirmedReservations);
    }
}
