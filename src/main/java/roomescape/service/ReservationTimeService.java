package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeRq;
import roomescape.dto.ReservationTimeRs;
import roomescape.repository.ReservationTimeRepo;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepo reservationTimeRepo;

    public ReservationTimeService(ReservationTimeRepo reservationTimeRepo) {
        this.reservationTimeRepo = reservationTimeRepo;
    }

    public List<ReservationTimeRs> getAllReservationTimes() {
        return reservationTimeRepo.findAll().stream()
                .map(reservationTime -> new ReservationTimeRs(reservationTime.getId(), reservationTime.getStartAt()))
                .toList();
    }

    public ReservationTimeRs addReservationTime(ReservationTimeRq reservationTimeRq) {
        Long id = reservationTimeRepo.save(reservationTimeRq);
        return new ReservationTimeRs(id, reservationTimeRq.getStartAt());
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepo.deleteById(id);
    }
}
