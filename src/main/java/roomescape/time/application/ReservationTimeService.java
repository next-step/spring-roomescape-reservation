package roomescape.time.application;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.time.domain.ReservationTime;
import roomescape.time.infrastructure.JdbcReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final JdbcReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(JdbcReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse createReservationTime(ReservationTime reservationTime) {
        ReservationTime savedResponseTime = reservationTimeRepository.save(reservationTime);
        return ReservationTimeResponse.from(savedResponseTime);
    }

    public List<ReservationTimeResponse> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void deleteReservationTime(Long reservationId) {
        reservationTimeRepository.delete(reservationId);
    }
}
