package roomescape.reservation.application;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public Reservation save(ReservationCreateRequest request) {
        ReservationTime reservationTime = request.toReservationTime();
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);
        Reservation reservation = request.toReservation(savedReservationTime);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public void cancelReservation(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
        reservationRepository.deleteById(reservationId);
    }
}
