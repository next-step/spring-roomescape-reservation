package roomescape.application.service;

import org.springframework.stereotype.Service;
import roomescape.application.mapper.ReservationViewMapper;
import roomescape.domain.reservation.ReservationViews;
import roomescape.repository.ReservationRepository;
import roomescape.repository.projection.ReservationViewProjection;

import java.util.List;

@Service
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;

    public ReservationQueryService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationViews findAllReservations() {
        List<ReservationViewProjection> reservationViewProjections =
                reservationRepository.findAllReservationViewProjection();

        return ReservationViewMapper.toReservationViews(reservationViewProjections);
    }
}
