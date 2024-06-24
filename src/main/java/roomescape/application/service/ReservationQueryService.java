package roomescape.application.service;

import org.springframework.stereotype.Service;
import roomescape.application.service.mapper.ReservationViewMapper;
import roomescape.domain.reservation.ReservationViews;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;

    public ReservationQueryService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationViews findAll() {
        return ReservationViewMapper.toReservationViews(reservationRepository.findAllReservationViewProjection());
    }
}
