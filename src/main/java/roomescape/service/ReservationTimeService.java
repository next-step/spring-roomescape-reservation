package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeRq;
import roomescape.dto.ReservationTimeRs;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeRs> getAllReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(reservationTime -> new ReservationTimeRs(reservationTime.getId(), reservationTime.getStartAt()))
                .toList();
    }

    public ReservationTimeRs addReservationTime(ReservationTimeRq reservationTimeRq) {
        Long id = reservationTimeRepository.save(reservationTimeRq);
        return new ReservationTimeRs(id, reservationTimeRq.getStartAt());
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
