package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.reservation.ReservationsResponse;
import roomescape.dto.reservation.create.ReservationCreateRequest;
import roomescape.dto.reservation.create.ReservationCreateResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationsResponse> findReservations() {
        return reservationRepository.findReservations()
                .stream()
                .map(ReservationsResponse::new)
                .collect(Collectors.toList());
    }

    public ReservationCreateResponse createReservation(ReservationCreateRequest request) {
        ReservationTime time = reservationTimeRepository.findReservationTimeById(request.getTimeId());
        Reservation reservation = reservationRepository.createReservation(request, time);
        return ReservationCreateResponse.fromEntity(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteReservation(id);
    }
}
