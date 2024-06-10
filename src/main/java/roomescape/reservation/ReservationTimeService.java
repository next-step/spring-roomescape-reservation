package roomescape.reservation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationTimeService {
    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    private ReservationTimeRepository reservationTimeRepository;

    public ReservationTime add(ReservationTime newReservationTime) {
        return reservationTimeRepository.addReservationTime(newReservationTime);
    }

    public List<ReservationTime> reservationTimes() {
        return reservationTimeRepository.reservationTimes();

    }

    public void delete(Long id) {
        reservationTimeRepository.delete(id);
    }
}
