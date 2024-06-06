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

    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        return reservationTimeRepository.save(reservationTime);
    }

    public List<ReservationTime> getReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public void deleteReservationTime(Long reservationId) {
        reservationTimeRepository.delete(reservationId);
    }
}
