package roomescape.reservation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationTimeService {
    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    private final ReservationTimeRepository reservationTimeRepository;

    public List<ReservationTime> reservationTimes() {
        return reservationTimeRepository
                .reservationTimes()
                .stream()
                .map(ReservationTime::new)
                .toList();
    }

    public ReservationTime add(ReservationTime newReservationTime) {
         ReservationTimeEntity entity = reservationTimeRepository.addReservationTime(newReservationTime.toEntity());
         return new ReservationTime(entity);
    }

    public void delete(Long id) {
        reservationTimeRepository.delete(id);
    }
}
