package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(@Autowired ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationRepository reservations() {
        return reservationRepository;
    }

    public Reservation add(Reservation newReservation) {
        return reservationRepository.add(newReservation);
    }

    public void delete(long id) {
        reservationRepository.delete(id);
    }
}
