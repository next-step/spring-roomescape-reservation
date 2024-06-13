package roomescape.reservation;

import org.springframework.stereotype.Service;
import roomescape.time.TimeEntity;
import roomescape.time.TimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> reservations() {
        return reservationRepository.reservations();
    }

    public Reservation add(NewReservation newReservation) {
        ReservationEntity entity =  reservationRepository.addReservation(newReservation.toEntity());
        TimeEntity time = timeRepository.time(entity.getTimeId());
        return new Reservation(entity, time);
    }

    public void delete(long id) {
        reservationRepository.deleteReservation(id);
    }
}
