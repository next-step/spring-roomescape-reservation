package roomescape.time.application;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.reservation.domain.repository.ReservationRepository;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.ReservationTimeCreateRequest;
import roomescape.time.exception.CannotDeleteReserveTimeException;
import roomescape.time.infrastructure.JdbcReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final JdbcReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationTimeService(JdbcReservationTimeRepository reservationTimeRepository,
            ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    public ReservationTimeResponse createReservationTime(ReservationTimeCreateRequest request) {
        ReservationTime savedResponseTime = reservationTimeRepository.save(request.toEntity());
        return ReservationTimeResponse.from(savedResponseTime);
    }

    public List<ReservationTimeResponse> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void deleteReservationTime(Long reservationTimeId) {
        if (reservationRepository.existsByReservationTimeId(reservationTimeId)) {
            throw new CannotDeleteReserveTimeException("예약된 시간은 삭제할 수 없습니다.");
        }
        reservationTimeRepository.delete(reservationTimeId);
    }
}
