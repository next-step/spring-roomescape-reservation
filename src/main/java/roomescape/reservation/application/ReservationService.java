package roomescape.reservation.application;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.reservation.application.dto.ReservationResponse;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.repository.ReservationRepository;
import roomescape.reservation.presentation.dto.ReservationCreateRequest;
import roomescape.time.domain.ReservationTime;
import roomescape.time.infrastructure.JdbcReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final JdbcReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository, JdbcReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationResponse save(ReservationCreateRequest request) {
        ReservationTime savedReservationTime = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다."));
        Reservation reservation = reservationRepository.save(request.toReservation(savedReservationTime));
        return ReservationResponse.from(reservation);
    }

    public List<ReservationResponse> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void cancelReservation(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
        reservationRepository.deleteById(reservationId);
    }
}
