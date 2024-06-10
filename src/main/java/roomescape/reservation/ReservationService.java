package roomescape.reservation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> reservations() {
        return reservationRepository.reservations();
    }

    public Reservation add(Reservation newReservation) {
        return reservationRepository.addReservation(newReservation);
    }

    public void delete(long id) {
        reservationRepository.deleteReservation(id);
    }
}
